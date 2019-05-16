package faq.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import faq.exception.FaqNotUniqueException;
import faq.exception.NotFoundException;
import faq.model.ClientFaq;
import faq.model.Faq;
import faq.model.Tag;
import faq.repository.FaqRepository;
import faq.repository.TagRepository;

@Service
@Transactional
public class FaqService {
	private final int MAX_PAGE_SIZE = 50;
	private final FaqRepository faqRepository;
	private final TagRepository tagRepository;

	public FaqService(FaqRepository faqRepository, TagRepository tagRepository) {
		this.faqRepository = faqRepository;
		this.tagRepository = tagRepository;
	}

	public Page<Faq> browse(String search, Integer page, Integer size, Sort sort) {
		Page<Faq> faqPage;
		PageRequest pageRequest = PageRequest.of(page, Math.min(size, MAX_PAGE_SIZE), sort);
		if (search.isEmpty()) {
			faqPage = this.faqRepository.findAll(pageRequest);
		} else {
			faqPage = this.faqRepository.search(search, pageRequest);
		}
		return faqPage;
	}

	public Faq one(Long id) {
		return this.faqRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public Faq createFaq(ClientFaq newFaq) {
		questionIsUniqueOrThrow(newFaq.getQuestion());

		Faq faq = this.faqRepository.save(new Faq(newFaq.getQuestion(), newFaq.getAnswer()));
		addTags(newFaq, faq);
		this.faqRepository.flush();
		return faq;
	}

	public Faq updateFaq(ClientFaq updatedFaq, Long id) {		
		Faq saved = this.faqRepository.findById(id)
				.map(faq -> {
					if (!faq.getQuestion().equalsIgnoreCase(updatedFaq.getQuestion())) {
						questionIsUniqueOrThrow(updatedFaq.getQuestion());
					}
					faq.setQuestion(updatedFaq.getQuestion());
					faq.setAnswer(updatedFaq.getAnswer());
					addTags(updatedFaq, faq);
					return faq;
				})
				.orElseThrow(() -> new NotFoundException(id));

		this.faqRepository.flush();
		return saved;
	}

	public void deleteById(Long id) {
		this.faqRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
		this.faqRepository.deleteById(id);
	}

	private void addTags(ClientFaq client, Faq faq) {
		client.getTagset().stream().forEach(name -> {
			Tag tag = this.tagRepository.findByName(name)
					.orElseGet(() -> {
						return this.tagRepository.save(new Tag(name));
					});
			faq.addTag(tag);
		});
	}
	
	private void questionIsUniqueOrThrow(String question) {
		Optional<Faq> found = this.faqRepository.findByQuestion(question);
		if (found.isPresent()) {
			throw new FaqNotUniqueException(found.get().getId());
		}
	}
}
