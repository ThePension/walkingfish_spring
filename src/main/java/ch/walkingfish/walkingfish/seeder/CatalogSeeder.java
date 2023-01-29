package ch.walkingfish.walkingfish.seeder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.service.CatalogService;

@Component
public class CatalogSeeder implements CommandLineRunner {

    @Autowired
    private CatalogService catalogService;

    @Override
    public void run(String... args) throws Exception {
        // Clean the database
        cleanDatabase();

        // Seed the catalog
        seedArticles();
    }

    /**
     * Clean the database
     */
    private void cleanDatabase() {
        // Delete all pictures
        catalogService.deleteAllPictures();

        // Delete all articles
        catalogService.deleteAllArticles();
    }

    /**
     * Seed the catalog
     *  - Create 4 articles : Bonnet vert
     *  - Create 4 articles : T-Shirt rouge
     */
    private void seedArticles() {
        // Create an list of sizes for the article
        ArrayList<String> bonnet_sizes = new ArrayList<String>();
        bonnet_sizes.add("Taille unique");

        // Create 10 articles : Bonnet vert
        for (int i = 0; i < 4; i++) {
            // Create an article
            Article article = new Article("Bonnet vert",
                    "Bonnet vert en laine. Idéal pour les froides journées d'hiver.", 20d, "Bonnet", bonnet_sizes);

            // Save the article
            article = catalogService.addArticleToCatalog(article);

            // Create some pictures
            Picture picture1 = new Picture("/articlesImages/bonnet_vert1.jpeg", "bonnet_vert1.jpeg", article);
            Picture picture2 = new Picture("/articlesImages/bonnet_vert2.jpeg", "bonnet_vert2.jpeg", article);
            Picture picture3 = new Picture("/articlesImages/bonnet_vert3.png", "bonnet_vert3.png", article);
            Picture picture4 = new Picture("/articlesImages/bonnet_vert4.jpeg", "bonnet_vert4.jpeg", article);
            Picture picture5 = new Picture("/articlesImages/bonnet_vert5.jpeg", "bonnet_vert5.jpeg", article);
            Picture picture6 = new Picture("/articlesImages/bonnet_vert6.png", "bonnet_vert6.png", article);

            // Save the pictures
            catalogService.savePicture(picture1);
            catalogService.savePicture(picture2);
            catalogService.savePicture(picture3);
            catalogService.savePicture(picture4);
            catalogService.savePicture(picture5);
            catalogService.savePicture(picture6);
        }

        // Create an list of sizes for the article
        ArrayList<String> tshirt_sizes = new ArrayList<String>();
        tshirt_sizes.add("S");
        tshirt_sizes.add("M");
        tshirt_sizes.add("L");
        tshirt_sizes.add("XL");

        // Create 10 articles : T-Shirt rouge
        for (int i = 0; i < 4; i++) {
            // Create an article
            Article article = new Article("T-Shirt rouge",
                    "T-Shirt rouge en coton. Idéal pour les chaudes journées d'été.", 15d, "T-Shirt", tshirt_sizes);

            // Save the article
            article = catalogService.addArticleToCatalog(article);

            // Create some pictures
            Picture picture1 = new Picture("/articlesImages/tshirt_rouge1.jpeg", "tshirt_rouge1.jpeg", article);
            Picture picture2 = new Picture("/articlesImages/tshirt_rouge2.jpeg", "tshirt_rouge2.jpeg", article);
            Picture picture3 = new Picture("/articlesImages/tshirt_rouge3.jpeg", "tshirt_rouge3.jpeg", article);
            Picture picture4 = new Picture("/articlesImages/tshirt_rouge4.jpeg", "tshirt_rouge4.jpeg", article);
            Picture picture5 = new Picture("/articlesImages/tshirt_rouge5.png", "tshirt_rouge5.png", article);
            Picture picture6 = new Picture("/articlesImages/tshirt_rouge6.jpeg", "tshirt_rouge6.jpeg", article);

            // Save the pictures
            catalogService.savePicture(picture1);
            catalogService.savePicture(picture2);
            catalogService.savePicture(picture3);
            catalogService.savePicture(picture4);
            catalogService.savePicture(picture5);
            catalogService.savePicture(picture6);
        }
    }
}
