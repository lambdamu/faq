package faq.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import faq.model.Tag;
import faq.repository.TagRepository;

@Service
public class TagService {
	private final int MAX_PAGE_SIZE = 100;
	private final TagRepository tagRepository;
	
	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	public Page<Tag> all(Integer page, Integer size) {
		return this.tagRepository.findAll(PageRequest.of(page, Math.min(size, MAX_PAGE_SIZE)));
	}

	public List<Tag> find(String containing) {
		return this.tagRepository.findAllByNameContaining(containing, PageRequest.of(0, 10));
	}
}
