package ch.walkingfish.walkingfish.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Picture {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String url;

    public Picture() {
    }

    public Picture(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
