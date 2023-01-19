package ch.walkingfish.walkingfish.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Show the catalogue
     * 
     * @param model the model to add the articles to
     * @return the view to show
     */
    @GetMapping(value = { "/", "" })
    public String showCatalogue(Model model, @RequestParam(required = false) String search) {
        List<Article> articles = null;

        if (search != null) {
            articles = catalogService//
                    .getAllArticlesFromCatalog()//
                    .stream()//
                    .filter(a -> a.getName().contains(search) || a.getDescription().contains(search))
                    .collect(Collectors.toList());
        } else {
            articles = catalogService.getAllArticlesFromCatalog();
        }

        model.addAttribute("isAdmin", Boolean.FALSE);
        model.addAttribute("articles", articles);
        model.addAttribute("search", search); // Used to keep the search term in the search bar

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
