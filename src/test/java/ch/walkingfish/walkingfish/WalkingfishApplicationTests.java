package ch.walkingfish.walkingfish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.walkingfish.walkingfish.model.Article;

@SpringBootTest
@AutoConfigureMockMvc
class WalkingfishApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
    private ObjectMapper objectMapper;

	@Test
	void contextLoads() throws Exception {
		this.mockMvc
				.perform(get("/")) //
				.andExpect(status().isOk());

	}

	@Test
    public void testSaveAndUpdateCatalogue() throws Exception {
        Article article = new Article();

		// Random article
		article.setName("Test article");
		article.setDescription("Test article description");
		article.setPrice(10.0);
		article.setType("Test type");


        String json = objectMapper.writeValueAsString(article);

        String location = mockMvc.perform(post(("/catalogue/save"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
				.andExpect(status().is3xxRedirection())
				.andReturn().getResponse().getHeader("Location");

		Assertions.assertEquals("/catalogue", location);

		// Update article
		article.setName("Test article updated");

		json = objectMapper.writeValueAsString(article);

		location = mockMvc.perform(post(("/catalogue/update"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().is3xxRedirection())
				.andReturn().getResponse().getHeader("Location");

		Assertions.assertEquals("/catalogue", location);
    }

}
