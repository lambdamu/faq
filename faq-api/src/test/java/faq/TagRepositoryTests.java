package faq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import faq.model.Tag;
import faq.repository.TagRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TagRepositoryTests {
	@Autowired
	private TagRepository repository;
	
	@Test
	public void testGuess() throws Exception {
		List<Tag> res = this.repository.findAllByNameContaining("galax", PageRequest.of(0, 10));
		assertEquals(1, res.size());
	}
	
	@Test
	public void testFindByName() throws Exception {
		Optional<Tag> res = this.repository.findByName("astronomy");
		assertTrue(res.isPresent());
		
		res = this.repository.findByName("galax");
		assertFalse(res.isPresent());
	}
}