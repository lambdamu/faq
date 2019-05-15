package faq.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import faq.model.ClientFaq;
import faq.model.Faq;
import faq.resource.FaqResource;
import faq.resource.FaqResourceAssembler;
import faq.service.FaqService;

@RestController
@RequestMapping(value = "/api/faqs", produces = "application/json")
public class FaqController {
	private final FaqService faqService;
	private final FaqResourceAssembler faqAssembler;
	private final PagedResourcesAssembler<Faq> pageAssembler;
	
	public FaqController(FaqService faqService, FaqResourceAssembler faqAssembler, PagedResourcesAssembler<Faq> pageAssembler) {
		this.faqService = faqService;
		this.faqAssembler = faqAssembler;
		this.pageAssembler = pageAssembler;
	}

	@GetMapping()
	public PagedResources<FaqResource> browse(
			@RequestParam(required = false, defaultValue = "") String search,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "20") Integer size) {
		Page<Faq> faqPage = this.faqService.browse(search, page, size, Sort.by(Direction.DESC, "id"));
		return this.pageAssembler.toResource(faqPage, this.faqAssembler);
	}

	@GetMapping("/{id}")
	public FaqResource one(@PathVariable("id") Long id) {
		Faq faq = this.faqService.one(id);
		return this.faqAssembler.toResource(faq);
	}

	@PostMapping()
	public ResponseEntity<?> createFaq(@RequestBody ClientFaq newFaq) throws URISyntaxException {
		final Faq faq = this.faqService.createFaq(newFaq);
		FaqResource resource = this.faqAssembler.toResource(faq);
		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	@PutMapping("/{id}")
	public FaqResource updateFaq(@RequestBody ClientFaq updatedFaq, @PathVariable Long id) {
		Faq saved = this.faqService.updateFaq(updatedFaq, id);
		return this.faqAssembler.toResource(saved);
	}

	@DeleteMapping("/{id}")
	public void deleteFaq(@PathVariable Long id) {
		this.faqService.deleteById(id);
	}
}
