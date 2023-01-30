package ch.walkingfish.walkingfish.repository;

import org.springframework.data.repository.CrudRepository;

import ch.walkingfish.walkingfish.model.Colori;

public interface ColoriRepository extends CrudRepository<Colori, Long> {
    public Colori findById(long id);
    public Colori findByName(String name);
    public Colori findByHexa(String hexa);
}
