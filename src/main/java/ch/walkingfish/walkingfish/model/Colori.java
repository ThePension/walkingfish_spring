package ch.walkingfish.walkingfish.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Colori {    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String hexa;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "coloris")
    private Set<Article> articles;

    /**
     * Default constructor
     */
    public Colori() {
    }

    /**
     * Constructor
     * 
     * @param name name of the color
     * @param hexa hexa code of the color
     */
    public Colori(String name, String hexa) {
        this.name = name;
        this.hexa = hexa;
    }

    /**
     * Get the articles
     * 
     * @return the articles
     */
    public Set<Article> getArticles() {
        return this.articles;
    }

    /**
     * Set the articles
     * 
     * @param articles the articles
     */
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    /**
     * Add an article
     * 
     * @param article the article
     */
    public void addArticle(Article article) {
        this.articles.add(article);
    }

    /**
     * Remove an article
     * 
     * @param article the article
     */
    public void removeArticle(Article article) {
        this.articles.remove(article);
    }

    /**
     * Remove an article
     * 
     * @return the article
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id
     * 
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     * 
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the hexa code
     * 
     * @return the hexa code
     */
    public String getHexa() {
        return hexa;
    }

    /**
     * Set the hexa code
     * 
     * @param hexa the hexa code
     */
    public void setHexa(String hexa) {
        this.hexa = hexa;
    }

    @Override
    public String toString() {
        return "Picture[article_id : this_id : " + this.id
                + "|this_name : " + this.name
                + "|this_hexa : " + this.hexa
                + "]";
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Colori other = (Colori) obj;

        if (!this.name.equals(other.name))
            return false;

        if (!this.hexa.equals(other.hexa))
            return false;

        return true;
    }
}
