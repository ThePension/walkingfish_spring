package ch.walkingfish.walkingfish.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String type;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Picture> pictures;

    public Article() {
    }

    public Article(String name, String description, Double price, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public List<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "Article [description=" + description + ", id=" + id + ", name=" + name + ", price=" + price + ", type="
                + type + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // Test if other is an instance of Article
        Article other = (Article) obj;

        if (this == other) {
            return true;
        }

        if (!this.id.equals(other.getId())){
            return false;
        }
        
        if (!this.name.equals(other.getName())) {
            return false;
        }

        if (!this.description.equals(other.getDescription())) {
            return false;
        }
        
        if (!this.price.equals(other.getPrice()))
        {
            return false;
        }
        
        if (!this.type.equals(other.getType())) {
            return false;
        }

        return true;
    }
}
