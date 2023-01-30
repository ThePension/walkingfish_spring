package ch.walkingfish.walkingfish.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.service.CatalogService;
import ch.walkingfish.walkingfish.service.ColoriService;
import ch.walkingfish.walkingfish.service.FileStorageService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ColoriService coloriService;

    private final int pageSize = 4;

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

        model.addAttribute("isAdmin", Boolean.TRUE);
        model.addAttribute("articles", articles);

        return "catalogue";
    }

    /**
     * Show the form to create a new article
     * 
     * @param model the model to add the article to
     * @return the view to show
     */
    @GetMapping(value = { "/create" })
    public String showNewArticle(Model model) {
        model.addAttribute("article", new Article());

        model.addAttribute("isNew", Boolean.TRUE);
        model.addAttribute("isEdit", Boolean.FALSE);
        model.addAttribute("sizes", Article.SIZES);

        return "new-article";
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
            return "redirect:/admin";
        }

        model.addAttribute("article", article);

        model.addAttribute("isAdmin", Boolean.TRUE);

        return "show-article";
    }

    /**
     * Save the article and the images to the database and the server
     * 
     * @param article the article to save
     * @param images  the images to save
     * @param model   the model to add the article to
     * @return the view to show
     */
    @PostMapping(value = "/save")
    public String saveArticle(Article article, @RequestParam("images") MultipartFile[] images, Model model) {
        // Save the article to the database
        article = catalogService.addArticleToCatalog(article);

        // Save the images to the server
        for (MultipartFile image : images) {
            if (image.getOriginalFilename()!= null && image.getOriginalFilename() != ""){
                try {
                    String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

                    fileStorageService.save(image, imageName);

                    // Save the picture to the database
                    Picture picture = new Picture("/articlesImages/" + imageName, imageName, article);

                    catalogService.savePicture(picture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "redirect:/admin";
    }

    /**
     * Show the form to edit the article with the given id
     * 
     * @param id    the id of the article to edit
     * @param model the model to add the article to
     * @return the view to show
     */
    @GetMapping(value = "/edit/{id}")
    public String showUpdateArticle(@PathVariable int id, Model model) {
        Article articleToEdit = null;
        try {
            articleToEdit = catalogService.getArticleById((long) id);
        } catch (Exception e) {
            return "redirect:/admin";
        }

        model.addAttribute("article", articleToEdit);

        model.addAttribute("isNew", Boolean.FALSE);
        model.addAttribute("isEdit", Boolean.TRUE);
        model.addAttribute("sizes", Article.SIZES);

        return "new-article";
    }

    /**
     * Update the article in the database
     * 
     * @param article the article to update
     * @param errors  the errors
     * @param model   the model to add the article to
     * @return the view to show
     */
    @PostMapping(value = "/update")
    public String updateArticleInDB(@ModelAttribute Article article, BindingResult errors, Model model) {
        catalogService.updateArticleInDB(article);

        return "redirect:/admin/show/" + article.getId();
    }

    /**
     * Delete the article with the given id
     * 
     * @param id    the id of the article to delete
     * @param model the model to add the article to
     * @return the view to show
     */
    @PostMapping(value = "/delete")
    public String deleteArticleInDB(@ModelAttribute("id") Integer id, Model model) {
        Article article = null;

        // Get the article from the database
        try {
            article = catalogService.getArticleById(id.longValue());
        } catch (Exception e) {
            model.addAttribute("errors", "Une erreur est survenue lors de la suppression de l'article");
            return "redirect:/admin";
        }

        // Delete the pictures from the server
        for (Picture picture : article.getPictures()) {
            try {
                fileStorageService.delete(picture.getName());
            } catch (IOException e) {
                model.addAttribute("errors", "Une erreur est survenue lors de la suppression de l'article");
                e.printStackTrace();
            }
        }

        // Delete the article from the database
        for (Picture picture : article.getPictures()) {
            try {
                catalogService.deletePictureInDB(picture.getId());
            } catch (Exception e) {
                model.addAttribute("errors", "Une erreur est survenue lors de la suppression de l'article");
                e.printStackTrace();
            }
        }

        catalogService.deleteArticleInDB(id.longValue());

        return "redirect:/admin";
    }

    /**
     * Delete the picture with the given id
     * 
     * @param picture_id the id of the picture to delete
     * @param article_id the id of the article to redirect to
     * @param model      the model to add the picture to
     * @return the view to show
     */
    @PostMapping(value = "/picture/delete")
    public String deletePictureInDB(@ModelAttribute("picture_id") Integer picture_id,
            @ModelAttribute("article_id") Integer article_id, Model model) {
        Picture picture = null;

        // Get the picture from the database
        try {
            picture = catalogService.getPictureById(picture_id.longValue());
        } catch (Exception e) {
            model.addAttribute("errors", "Une erreur est survenue lors de la suppression de l'image");
            e.printStackTrace();
            return "redirect:/admin/show/" + article_id;
        }

        // Delete the picture from the database
        try {
            catalogService.deletePictureInDB(picture_id.longValue());
        } catch (Exception e) {
            model.addAttribute("errors", "Une erreur est survenue lors de la suppression de l'image");
            e.printStackTrace();
            return "redirect:/admin/show/" + article_id;
        }

        // Delete the picture from the server
        try {
            fileStorageService.delete(picture.getName());
        } catch (IOException e) {
            model.addAttribute("errors", "Une erreur est survenue lors de la suppression de l'image");
            e.printStackTrace();
            return "redirect:/admin/show/" + article_id;
        }

        return "redirect:/admin/show/" + article_id;
    }

    @PostMapping(value = "/picture/save")
    public String addPictureToArticle(@ModelAttribute("article_id") Integer article_id,
            @RequestParam("images") MultipartFile[] images, Model model) {
        // Get the article from the database
        Article article;

        try {
            article = catalogService.getArticleById(article_id.longValue());
        } catch (Exception e) {
            model.addAttribute("errors", "Une erreur est survenue lors de l'ajout d'image(s) à l'article");
            e.printStackTrace();
            return "redirect:/admin/show/" + article_id;
        }

        // Save the images to the server
        for (MultipartFile image : images) {
            try {
                String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

                fileStorageService.save(image, imageName);

                // Save the picture to the database
                Picture picture = new Picture("/articlesImages/" + imageName, imageName, article);

                catalogService.savePicture(picture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/admin/show/" + article_id;
    }

    @GetMapping(value = {"/coloris", "/coloris/"})
    public String showColoris(Model model) {
        List<Colori> coloris = coloriService.getAllColori();

        model.addAttribute("coloris", coloris);
        model.addAttribute("colori", new Colori());

        return "coloris";
    }

    @PostMapping(value = "/colori/delete")
    public String deletePictureInDB(@ModelAttribute("id") Integer id, Model model) {
        try {
            coloriService.deleteColori(id.longValue());
        } catch (Exception e) {
            model.addAttribute("errors", "Une erreur est survenue lors de la suppression du colori");
            e.printStackTrace();
            return "redirect:/admin/coloris";
        }

        return "redirect:/admin/coloris";
    }

    @PostMapping(value = "/colori/save")
    public String addColori(@ModelAttribute("colori") Colori colori, Model model) {
        try {
            coloriService.addColori(colori);
        } catch (Exception e) {
            model.addAttribute("errors", "Une erreur est survenue lors de l'ajout du colori");
            e.printStackTrace();
            return "redirect:/admin/coloris";
        }

        return "redirect:/admin/coloris";
    }
}
