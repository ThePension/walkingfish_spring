package ch.walkingfish.walkingfish.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.walkingfish.walkingfish.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findById(long id);

    List<Article> findAll();
}
