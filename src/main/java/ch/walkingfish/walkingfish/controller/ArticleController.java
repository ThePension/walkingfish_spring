package ch.walkingfish.walkingfish.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.service.CatalogService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/catalogue")
public class ArticleController {

    @Autowired
    CatalogService catalogService;

    private final int pageSize = 6;

    /**
     * Show the catalogue
     * 
     * @param model the model to add the articles to
     * @return the view to show
     */
    @GetMapping(value = { "/", "" })
    public String showCatalogue(Model model, @RequestParam("search") Optional<String> opt_search, @RequestParam("page") Optional<String> opt_page) {
        Page<Article> articles = null;
        int currentPage = opt_page.isPresent() ? Integer.parseInt(opt_page.get()) : 1;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        if (opt_search.isPresent()) {
            String search = opt_search.get();
            articles = catalogService.findPaginatedAndFiltered(pageable, search);

            model.addAttribute("search", search); // Used to keep the search term in the search bar
        } else {
            articles = catalogService.findPaginated(pageable);
        }

        int totalPages = articles.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream//
                .rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
                
            model.addAttribute("pageNumbers", pageNumbers);
        }


        model.addAttribute("isAdmin", Boolean.FALSE);
        model.addAttribute("articles", articles);

        return "catalogue";
    }

    /**
     * Show the article with the given id
     * 
     * @param id    the id of the article to show
     * @param model the model to add the article to
     * @return the view to show
     */
    @GetMapping(value = "/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        Article article = null;
        try {
            article = catalogService.getArticleById((long) id);
        } catch (Exception e) {
            return "redirect:/catalogue";
        }

        model.addAttribute("article", article);

        model.addAttribute("isAdmin", Boolean.FALSE);

        return "show-article";
    }
}
