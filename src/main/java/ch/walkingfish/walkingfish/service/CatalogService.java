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
	 * Return all the articles in the catalog
	 * 
	 * @return the list of articles
	 */
	public List<Article> getAllArticlesFromCatalog() {
		List<Article> result = new ArrayList<Article>();
		articleRepository.findAll().forEach(result::add);

		return result;
	}

	/**
	 * Add a new article to the catalog
	 * 
	 * @param article the article to add
	 * @return the article added
	 */
	public Article adArticleToCatalog(Article article) {
		return articleRepository.save(article);
	}

	/**
	 * Return the article with the given id
	 * 
	 * @param id the id of the article
	 * @return the article
	 * @throws Exception if the article does not exist
	 */
	public Article getArticleById(Long id) throws Exception {
		Optional<Article> article = articleRepository.findById(id);

		if (article.isPresent())
			return article.get();

		throw new Exception("This article does not exist");
	}

	/**
	 * Update the article in the database
	 * 
	 * @param article the article to update
	 * @return the article updated
	 */
	public Article updateArticleInDB(Article article) {
		return articleRepository.save(article);
	}

	/**
	 * Delete the article in the database
	 * 
	 * @param article the article to delete
	 */
	public void deleteArticleInDB(Article article) {
		articleRepository.delete(article);
	}

	/**
	 * Delete the article in the database
	 * 
	 * @param id the id of the article to delete
	 */
	public void deleteArticleInDB(Long id) {
		articleRepository.deleteById(id);
	}

	/**
	 * Delete the picture in the database
	 * 
	 * @param id the id of the picture to delete
	 * @throws Exception if the picture does not exist
	 */
	public void deletePictureInDB(Long id) throws Exception {
		Optional<Picture> optPicture = pictureRepository.findById(id);

		if (optPicture.isPresent()) {
			Picture picture = optPicture.get();

			pictureRepository.delete(picture);
		}
		else
		{
			throw new Exception("This picture does not exist");
		}
	}

	/**
	 * Save the picture in the database
	 * 
	 * @param picture the picture to save
	 */
	public void savePicture(Picture picture) {
		pictureRepository.save(picture);
	}

	/**
	 * Return the picture with the given id
	 * 
	 * @param id the id of the picture
	 * @return the picture
	 * @throws Exception if the picture does not exist
	 */
	public Picture getPictureById(Long id) throws Exception {
		Optional<Picture> optPicture = pictureRepository.findById(id);

		if (optPicture.isPresent()) {
			return optPicture.get();
		}
		else
		{
			throw new Exception("This picture does not exist");
		}
	}
}
