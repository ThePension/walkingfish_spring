package ch.walkingfish.walkingfish.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.repository.ArticleRepository;
import ch.walkingfish.walkingfish.repository.PictureRepository;
import ch.walkingfish.walkingfish.model.*;


@Service
public class CatalogService {

    @Autowired
	ArticleRepository articleRepository;

	@Autowired
	PictureRepository pictureRepository;

    /**
	 * Retourne tous les articles
	 * @return la liste des articles
	 */
	public List<Article> getAllArticlesFromCatalog() {
		List<Article> result = new ArrayList<Article>();
		articleRepository.findAll().forEach(result::add);

		return result;
	}

    public Article addBeerToCatalog(Article article) {
        return articleRepository.save(article);
    }

	public Article getArticleById(Long id) throws Exception
	{
		Optional<Article> article = articleRepository.findById(id);

		if (article.isPresent()) return article.get();

		throw new Exception("This beer does not exist");
	}

	public Article updateArticleInDB(Article article)
	{
		// TODO : Fix this
		articleRepository.delete(article);

		return articleRepository.save(article);
	}

	public void deleteArticleInDB(Article article)
	{
		articleRepository.delete(article);
	}

	public void deleteArticleInDB(Long id)
	{
		articleRepository.deleteById(id);
	}

	public void deletePictureInDB(Long id)
	{
		Optional<Picture> optPicture = pictureRepository.findById(id);

		if (optPicture.isPresent())
		{
			Picture picture = optPicture.get();
			// Article article = picture.getArticle();

			// article.removePicture(picture);

			// articleRepository.save(article);

			pictureRepository.delete(picture);			
		}
	}

	public void savePicture(Picture picture)
	{
		pictureRepository.save(picture);
	}

	public void addPicturesToArticle(List<Picture> pictures, Article article)
	{
		for (Picture picture : pictures)
		{
			// picture.setArticle(article);
			pictureRepository.save(picture);
		}
	}
}
