package ch.walkingfish.walkingfish.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.repository.ColoriRepository;
import ch.walkingfish.walkingfish.model.Colori;

@Service
public class ColoriService {
    @Autowired
    private ColoriRepository coloriRepository;

    /**
     * Return all the colori
     * @return the list of colori
     */
    public List<Colori> getAllColori() {
        List<Colori> result = new ArrayList<Colori>();
        coloriRepository.findAll().forEach(result::add);

        return result;
    }

    /**
     * Return the colori by the id
     * @param id the id of the colori
     * @return the colori
     */
    public Colori getColoriById(long id) {
        return coloriRepository.findById(id);
    }

    /**
     * Add a new colori
     * @param colori the colori to add
     */
    public void addColori(Colori colori) {
        coloriRepository.save(colori);
    }

    /**
     * Update the colori
     * @param colori the colori to update
     */
    public void updateColori(Colori colori) {
        coloriRepository.save(colori);
    }

    /**
     * Delete the colori by the id
     * @param id the id of the colori
     */
    public void deleteColori(long id) {
        // Remove all references to this colori
        Colori colori = getColoriById(id);
        colori.getArticles().forEach(a -> a.removeColori(colori));
        colori.getArticles().clear();

        coloriRepository.deleteById(id);
    }

    /**
     * Return the colori by the name
     * @param name the name of the colori
     * @return the colori
     */
    public Colori getColoriByName(String name) {
        return coloriRepository.findByName(name);
    }

    /**
     * Return the colori by the hexa code
     * @param hexa the hexa code
     * @return the colori
     */
    public Colori getColoriByHexa(String hexa) {
        return coloriRepository.findByHexa(hexa);
    }

    /**
     * Delete all the colori
     */
    public void deleteAllColori() {
        // Get all the colori
        List<Colori> colori = getAllColori();

        // Delete all references to articles
        colori.stream()//
            .filter(c -> c.getArticles() != null)//
            .forEach(c -> c.getArticles().clear());

        // Delete all the colori
        colori.forEach(c -> deleteColori(c.getId()));
    }
}
