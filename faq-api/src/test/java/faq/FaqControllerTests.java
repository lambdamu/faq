package faq;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import faq.model.ClientFaq;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
public class FaqControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testList() throws Exception {
		this.mockMvc.perform(get("/api/faqs")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		// TODO
	}
	
	@Test
	public void testPaging() throws Exception {
		this.mockMvc.perform(get("/api/faqs?page=0&size=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		// TODO
		this.mockMvc.perform(get("/api/faqs?page=1&size=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		// TODO
	}

	@Test
	public void testSearch() throws Exception {
		this.mockMvc.perform(get("/api/faqs?search=42")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		// TODO
	}

	@Test
	public void testGet() throws Exception {
		this.mockMvc.perform(get("/api/faqs/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		// TODO
	}

	@Test
	@WithMockUser(roles="USER")
	public void testDeleteByUser() throws Exception {
		this.mockMvc.perform(delete("/api/faqs/2")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
				.andDo(print());
		// TODO
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	public void testDeleteByAdmin() throws Exception {
		this.mockMvc.perform(delete("/api/faqs/2")
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
				.andDo(print());
		// TODO
	}

	@Test
	@WithMockUser(roles="ADMIN")
	public void testUpdateByAdmin() throws Exception {
		ClientFaq faq = new ClientFaq("How do I?", "This is the way.");
		faq.addTag("astronomy");
		faq.addTag("test");
		this.mockMvc.perform(put("/api/faqs/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(faq))
				.with(csrf()))
				.andDo(print());
		// TODO
		this.mockMvc.perform(get("/api/faqs")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
	}

	@Test
	@WithMockUser(roles="ADMIN")
	public void testCreate() throws Exception {
		ClientFaq faq = new ClientFaq("How do I?", "This is the way.");
		faq.addTag("astronomy");
		faq.addTag("test");
		this.mockMvc.perform(post("/api/faqs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(faq))
				.with(csrf()))
				.andDo(print());
		// TODO
		this.mockMvc.perform(get("/api/faqs")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
	}
	


	@Test
	@WithMockUser(roles="ADMIN")
	public void testUnique() throws Exception {
		ClientFaq faq1 = new ClientFaq("How do I add tags?", "This is the way.");
		faq1.addTag("astronomy");
		faq1.addTag("test");
		this.mockMvc.perform(post("/api/faqs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(faq1))
				.with(csrf()))
				.andDo(print());
		
		ClientFaq faq2 = new ClientFaq(faq1.getQuestion(), "This is the way.");
		faq2.addTag("test");
		this.mockMvc.perform(post("/api/faqs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(faq2))
				.with(csrf()))
				.andDo(print());
		
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	public void testValidation() throws Exception {
		ClientFaq faq1 = new ClientFaq("", "");
		this.mockMvc.perform(post("/api/faqs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(faq1))
				.with(csrf()))
				.andDo(print());
	}
}