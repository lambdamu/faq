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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import faq.model.Faq;
import faq.model.Tag;
import faq.repository.FaqRepository;
import faq.repository.TagRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class FaqRepositoryTests {
	@Autowired
	private FaqRepository faqRepository;
	@Autowired
	private TagRepository tagRepository;
	
	@Test
	public void testSave() throws Exception {
		Tag tag1 = this.tagRepository.save(new Tag("tag1"));
		Tag tag2 = this.tagRepository.save(new Tag("tag2"));
		Faq faq = new Faq("q", "a");
		faq.addTag(tag1);
		faq.addTag(tag2);
		
		faq = this.faqRepository.save(faq);
		
		Optional<Faq> foundFaq = this.faqRepository.findById(faq.getId());
		assertTrue(foundFaq.isPresent());
		assertEquals(foundFaq.get().getQuestion(), foundFaq.get().getQuestion());
		assertEquals(foundFaq.get().getAnswer(), foundFaq.get().getAnswer());
		assertEquals(2, foundFaq.get().getTags().size());
		assertTrue(foundFaq.get().getTagSet().contains(tag1.getName()));
		assertTrue(foundFaq.get().getTagSet().contains(tag2.getName()));
	}
	
	@Test
	public void findAll() throws Exception {
		List<Faq> faqs = this.faqRepository.findAll();
		faqs.stream().forEach(System.out::println);
	}
	
	@Test
	public void testDelete() throws Exception {
		Tag tag = this.tagRepository.save(new Tag("tag"));
		Faq faq = new Faq("q", "a");
		faq.addTag(tag);

		this.faqRepository.save(faq);
		
		Optional<Faq> foundFaq = this.faqRepository.findById(faq.getId());
		assertTrue(foundFaq.isPresent());
		assertTrue(foundFaq.get().hasTag(tag));
		
		this.faqRepository.delete(foundFaq.get());
		
		foundFaq = this.faqRepository.findById(faq.getId());
		assertFalse(foundFaq.isPresent());
		
		Optional<Tag> foundTag = this.tagRepository.findById(tag.getId());
		assertTrue(foundTag.isPresent());
	}

	@Test
	public void testSearch() throws Exception {
		Page<Faq> page = this.faqRepository.search("42", PageRequest.of(0, 100));
		assertEquals(1, page.getNumberOfElements());

		page = this.faqRepository.search("galaxy", PageRequest.of(0, 100));
		assertEquals(3, page.getNumberOfElements());
	}
}