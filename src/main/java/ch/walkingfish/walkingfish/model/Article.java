package ch.walkingfish.walkingfish.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
	private Set<Picture> pictures;

    public Article() {
        this.pictures = new HashSet<>();
    }

    public Article(String name, String description, Double price, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;

        this.pictures = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id)
    {
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
        return this.pictures.stream().toList();
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(Picture picture) {
        this.pictures.add(picture);
    }

    @Override
    public String toString() {
        return "Article [description=" + description + ", id=" + id + ", name=" + name + ", price=" + price + ", type=" + type + "]";
    }
}
