package ch.walkingfish.walkingfish.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.walkingfish.walkingfish.repository.ArticleRepository;
import ch.walkingfish.walkingfish.repository.PictureRepository;
import ch.walkingfish.walkingfish.model.*;

@Service
public class CatalogService {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	PictureRepository pictureRepository;

	@Autowired
	ColoriService coloriService;

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
	 * 
	 * @param pageable the page to display
	 * @return the article
	 */
	public Page<Article> findPaginated(Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Article> list;

		List<Article> articles = getAllArticlesFromCatalog();

		if (articles.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, articles.size());
			list = articles.subList(startItem, toIndex);
		}

		Page<Article> bookPage = new PageImpl<Article>(list, PageRequest.of(currentPage, pageSize), articles.size());

		return bookPage;
	}

	/**
	 * 
	 * @param pageable
	 * @param search
	 * @return
	 */
	public Page<Article> findPaginatedAndFiltered(Pageable pageable, String search) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Article> list;

		List<Article> articles = getAllArticlesFromCatalog()//
				.stream()//
				.filter(a -> a.getName().contains(search) || a.getDescription().contains(search))
				.collect(Collectors.toList());

		if (articles.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, articles.size());
			list = articles.subList(startItem, toIndex);
		}

		Page<Article> bookPage = new PageImpl<Article>(list, PageRequest.of(currentPage, pageSize), articles.size());

		return bookPage;
	}

	/**
	 * Add a new article to the catalog
	 * 
	 * @param article the article to add
	 * @return the article added
	 */
	public Article addArticleToCatalog(Article article) {
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
		this.deleteArticleInDB(article.getId());
	}

	/**
	 * Delete the article in the database
	 * 
	 * @param id the id of the article to delete
	 */
	@Transactional
	public void deleteArticleInDB(Long id) {
		Optional<Article> article = articleRepository.findById(id);

		if (article.isPresent()) {
			// Delete attached pictures
			// article.get().getPictures().clear();

			// if (pictures != null) {
			// 	pictures.forEach(p -> pictureRepository.delete(p));
			// }

			// Remove references to coloris
			// article.get().getColoris().clear();

			// if (coloris != null) {
			// 	coloris.stream()//
			// 			.map(Colori::getArticles)
			// 			.filter(c -> c != null) //
			// 			.peek(c -> c.remove(article.get()))
			// 			.flatMap(Collection::stream)//
			// 			.peek(this::updateArticleInDB);

				// Save the coloris
				// coloris.forEach(c -> coloriService.updateColori(c));			
			// }
			// Delete the article
		articleRepository.deleteById(id);
		}
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

			// Remove the picture from the article
			Article article = picture.getArticle();
			article.getPictures().remove(picture);

			// Delete the picture
			pictureRepository.delete(picture);
		} else {
			throw new Exception("This picture does not exist");
		}
	}

	/**
	 * Save the picture in the database
	 * 
	 * @param picture the picture to save
	 */
	public Picture savePicture(Picture picture) {
		return pictureRepository.save(picture);
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
		} else {
			throw new Exception("This picture does not exist");
		}
	}

	/**
	 * Delete all the pictures in the database
	 */
	public void deleteAllPictures() {
		pictureRepository.findAll().forEach(p -> {
			try {
				deletePictureInDB(p.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * Delete all the articles in the database
	 */
	public void deleteAllArticles() {
		// Get all the articles
		List<Article> articles = getAllArticlesFromCatalog();

		// Delete all the articles
		articles.forEach(a -> {
			try {
				deleteArticleInDB(a.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
