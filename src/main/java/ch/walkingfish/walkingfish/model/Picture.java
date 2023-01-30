package ch.walkingfish.walkingfish.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;
    private String name;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    /**
     * Default constructor
     */
    public Picture() {
    }

    /**
     * Constructor
     * @param url url of the picture
     * @param name name of the picture
     * @param article the article
     */
    public Picture(String url, String name, Article article) {
        this.url = url;
        this.name = name;
        this.article = article;
    }

    /**
     * Get the article
     * @return the article
     */
    public Article getArticle() {
        return this.article;
    }

    /**
     * Set the article
     * @param article the article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * Get the id
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the url
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Picture[article_id : " + this.article.getId() + "|this_id : " + this.id + "|this_url : " + this.url
                + "|this_name : " + this.name
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

        Picture other = (Picture) obj;

        if (this.id != null)
        {
            if (other.id == null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        }

        if (!this.url.equals(other.getUrl()))
            return false;

        if (!this.name.equals(other.getName()))
            return false;

        return true;
    }
}
