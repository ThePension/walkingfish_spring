package ch.walkingfish.walkingfish.repository;

import org.springframework.data.repository.CrudRepository;

import ch.walkingfish.walkingfish.model.Picture;

public interface PictureRepository extends CrudRepository<Picture, Long> {

}
