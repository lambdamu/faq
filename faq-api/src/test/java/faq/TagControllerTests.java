package faq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
public class TagControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testList() throws Exception {
		this.mockMvc.perform(get("/api/tags")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		// TODO
	}
	
	@Test
	public void testFind() throws Exception {
		this.mockMvc.perform(get("/api/tags/search?containing=galax")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		// TODO
	}
}