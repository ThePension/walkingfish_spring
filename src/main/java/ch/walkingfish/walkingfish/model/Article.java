package ch.walkingfish.walkingfish.model;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.collection.internal.PersistentList;

@Entity
public class Article {

    public static final ArrayList<String> SIZES = new ArrayList<String>() {
        {
            add("XS");
            add("S");
            add("M");
            add("L");
            add("XL");
            add("XXL");
            add("Taille unique");
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String type;
    private ArrayList<String> sizes;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "article_colori", joinColumns = { @JoinColumn(name = "article_id") }, inverseJoinColumns = {
            @JoinColumn(name = "colori_id") })
    private List<Colori> coloris;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Picture> pictures;

    /**
     * Default constructor
     */
    public Article() {
    }

    /**
     * Constructor
     * 
     * @param name        The name of the article
     * @param description The description of the article
     * @param price       The price of the article
     * @param type        The type of the article
     * @param sizes       The sizes of the article
     * @param coloris     The coloris of the article
     */
    public Article(String name, String description, Double price, String type, ArrayList<String> sizes,
            ArrayList<Colori> coloris) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.sizes = sizes;
        this.coloris = coloris;

        for (Colori colori : coloris) {
            colori.getArticles().add(this);
        }
    }

    @PreRemove
    public void removeReferences() {
        for (Colori colori : coloris) {
            colori.getArticles().remove(this);
        }

        this.coloris.clear();
    }

    /**
     * Get the id of the article
     * 
     * @return The id of the article
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the article
     * 
     * @param id The id of the article
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the article
     * 
     * @return The name of the article
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the article
     * 
     * @param name The name of the article
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the article
     * 
     * @return The description of the article
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the article
     * 
     * @param description The description of the article
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the price of the article
     * 
     * @return The price of the article
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Set the price of the article
     * 
     * @param price The price of the article
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Set the type of the article
     * 
     * @param type The type of the article
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the type of the article
     * 
     * @return The type of the article
     */
    public String getType() {
        return type;
    }

    /**
     * Set the sizes of the article
     * 
     * @param sizes The sizes of the article
     */
    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }

    /**
     * Get the sizes of the article
     * 
     * @return The sizes of the article
     */
    public List<String> getSizes() {
        return this.sizes;
    }

    /**
     * Set the coloris of the article
     * 
     * @param coloris The coloris of the article
     */
    public void setColoris(ArrayList<Colori> coloris) {
        if (this.coloris == null) {
            this.coloris = new ArrayList<Colori>();
        }

        this.coloris.clear();
        this.coloris.addAll(coloris);
    }

    /**
     * Add a colori to the article
     * 
     * @param colori The colori to add
     */
    public void addColori(Colori colori) {
        this.coloris.add(colori);
        colori.addArticle(this);
    }

    /**
     * Remove a colori from the article
     * 
     * @param colori The colori to remove
     */
    public void removeColori(Colori colori) {
        this.coloris.remove(colori);
    }

    /**
     * Get the coloris of the article
     * 
     * @return The coloris of the article
     */
    public List<Colori> getColoris() {
        return this.coloris;
    }

    /**
     * Get the pictures of the article
     * 
     * @return The pictures of the article
     */
    public List<Picture> getPictures() {
        return this.pictures;
    }

    /**
     * Set the pictures of the article
     * 
     * @param pictures The pictures of the article
     */
    public void setPictures(List<Picture> _pictures) {
        System.out.println("setPictures");

        if (this.pictures == null) {
            this.pictures = new ArrayList<Picture>();
        }

        this.pictures.clear();
        this.pictures.addAll(_pictures);
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    /**
     * Test if two articles are equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Article other = (Article) obj;

        if (this.id != null) {
            if (!this.id.equals(other.getId())) {
                return false;
            }
        }

        if (!this.name.equals(other.getName())) {
            return false;
        }

        if (!this.description.equals(other.getDescription())) {
            return false;
        }

        if (!this.price.equals(other.getPrice())) {
            return false;
        }

        if (!this.type.equals(other.getType())) {
            return false;
        }

        return true;
    }
}
