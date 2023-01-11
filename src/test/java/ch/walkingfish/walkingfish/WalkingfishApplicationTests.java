package ch.walkingfish.walkingfish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.walkingfish.walkingfish.controller.ArticleController;
import ch.walkingfish.walkingfish.controller.IndexController;

@WebMvcTest(controllers = { IndexController.class, ArticleController.class})
class WalkingfishApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		this.mockMvc
				.perform(get("/")) //
				.andExpect(status().isOk()); // .andExpect(content().string("index"));

	}
	
	// @Test
	// void articleController_TestRoot() throws Exception {
	// 	this.mockMvc
	// 			.perform(get("/catalogue/")) //
	// 			.andExpect(status().isOk());
	// }

}
