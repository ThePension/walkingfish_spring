package ch.walkingfish.walkingfish.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.service.CatalogService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/catalogue")
public class ArticleController {
    
    @Autowired
    CatalogService catalogService;

    @GetMapping(value = {"/", ""})
	public String showCatalogue(Model model) {
        List<Article> articles = catalogService.getAllArticlesFromCatalog();

        model.addAttribute("isAdmin", Boolean.TRUE);
        model.addAttribute("articles", articles);

	    return "catalogue";
	}

    @GetMapping(value = {"/create"})
    public String showNewArticle(Model model) {
        model.addAttribute("article", new Article());

        model.addAttribute("isNew", Boolean.TRUE);
	    model.addAttribute("isEdit", Boolean.FALSE);
          
        return "new-article";
    }

    @PostMapping(value = "/save")
    public String saveArticle(Article article, @RequestParam("images") MultipartFile[] images, Model model) {
        article = catalogService.addBeerToCatalog(article);
        // Sauvegarder les images sur le serveur
        for (MultipartFile image : images) {
            try {
                String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                File save = new File("src\\main\\resources\\static\\articlesImages", imageName);

                Path path = Path.of(save.getAbsolutePath());

                System.out.println("Path : " + path);

                image.transferTo(path);

                Picture picture = new Picture("articlesImages/" + imageName, article);

                catalogService.savePicture(article, picture);
    
                article.addPicture(picture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return "redirect:/catalogue";
    }

    @GetMapping(value = "/edit/{id}")
    public String showUpdateArticle(@PathVariable int id, Model model)
	{
        Article articleToEdit = null;
        try {
            articleToEdit = catalogService.getArticleById((long)id);
        } catch (Exception e) {
            return "redirect:/catalogue";
        }

	    model.addAttribute("article", articleToEdit);

        model.addAttribute("isNew", Boolean.FALSE);
	    model.addAttribute("isEdit", Boolean.TRUE);

		return "new-article";
	}

    @PostMapping(value = "/update")
    public String updateArticleInDB(@ModelAttribute Article article, BindingResult errors, Model model)
	{
        catalogService.updateArticleInDB(article);

        return "redirect:/catalogue";
    }

    @PostMapping(value="/delete")
    public String deleteArticleInDB(@ModelAttribute("id") Integer id, Model model) {
        catalogService.deleteArticleInDB(id.longValue());
        
        return "redirect:/catalogue";
    }
    
}
