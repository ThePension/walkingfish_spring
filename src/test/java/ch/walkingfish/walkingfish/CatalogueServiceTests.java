package ch.walkingfish.walkingfish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import ch.walkingfish.walkingfish.model.*;

import ch.walkingfish.walkingfish.repository.ArticleRepository;
import ch.walkingfish.walkingfish.service.CatalogService;

@SpringBootTest
public class CatalogueServiceTests {

  @Autowired
  CatalogService catalogService;

  @Autowired
  ArticleRepository articleRepository;

  @Test
  void testInjectedComponentsAreNotNull(){
    assertThat(articleRepository).isNotNull();
    assertThat(catalogService).isNotNull();
  }

  @Test
  public void testGetAllArticles() {
      // Arrange
      List<Article> articles = Arrays.asList(
              new Article("Bonnet", "Bonnet de laine", 10.0, "Bonnet"),
              new Article("Veste", "Veste en cuir", 100.0, "Veste")
      );

      // Insert articles in database
      articles.stream().forEach(a -> catalogService.addArticleToCatalog(a));

      // Act
      List<Article> result = catalogService.getAllArticlesFromCatalog();

      // Assert
      assertThat(result).isNotEmpty();
  }

  @Test
  public void testAddArticleToCatalogue()
  {
    Article article = new Article("Veste", "Veste en cuir", 100.0, "Veste");

    // Insert article in database
    article = catalogService.addArticleToCatalog(article);

    assertThat(article).isNotNull();
  }

  @Test
  public void testGetArticleById() {
      // Arrange
      Article article = new Article("Veste", "Veste en cuir", 100.0, "Veste");

      // Insert article in database
      article = catalogService.addArticleToCatalog(article);

      // Act
      Article result = null;
      try {
        result = catalogService.getArticleById(article.getId());
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      // Assert
      assertThat(result).isEqualTo(article);
  }

  @Test
  public void testUpdateArticleInDB() {
      // Arrange
      Article article = new Article("Veste", "Veste en cuir", 100.0, "Veste");

      // Insert article in database
      article = catalogService.addArticleToCatalog(article);

      // Act
      article.setDescription("Veste en cuir de chèvre");
      article = catalogService.updateArticleInDB(article);

      // Assert
      assertThat(article.getDescription()).isEqualTo("Veste en cuir de chèvre");
  }

  @Test
  public void testDeleteArticleInDB() {
      // Arrange
      Article article = new Article("Veste", "Veste en cuir", 100.0, "Veste");

      // Insert article in database
      article = catalogService.addArticleToCatalog(article);

      // Act
      catalogService.deleteArticleInDB(article);

      // Try get the article
      Article result = null;
      try {
        result = catalogService.getArticleById(article.getId());
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // Assert
      assertThat(result).isNull();
  }

  @Test
  public void testSavePicture() {
      // Arrange
      Article article = new Article("Veste", "Veste en cuir", 100.0, "Veste");

      // Insert article in database
      article = catalogService.addArticleToCatalog(article);

      // Create picture
      Picture picture = new Picture("some/url/to/picture/", "Desert.jpg", article);

      // Insert picture in database
      picture = catalogService.savePicture(picture);

      // Get picture from database
      Picture result = null;
      try {
        result = catalogService.getPictureById(picture.getId());
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // Assert
      assertThat(picture).isNotNull();
      assertThat(result).isEqualTo(picture);

      try {
        catalogService.deletePictureInDB(picture.getId());
        catalogService.deleteArticleInDB(article.getId());
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
  }
}