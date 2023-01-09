package ch.walkingfish.walkingfish.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.repository.ArticleRepository;
import ch.walkingfish.walkingfish.model.*;


@Service
public class ArticleService {

    @Autowired
	ArticleRepository articleRepository;

    /**
	 * Retourne tous les articles
	 * @return la liste des articles
	 */
	public List<Article> getAllArticlesFromCatalog() {

		List<Article> result = new ArrayList<Article>();
		articleRepository.findAll().forEach(result::add);

		return result;
	}

    public void addBeerToCatalog(Article article) {
        articleRepository.save(article);
    }
}
