package faq.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import faq.model.Tag;
import faq.resource.TagResource;
import faq.resource.TagResourceAssembler;
import faq.service.TagService;

@RestController
@RequestMapping(value = "/api/tags", produces = "application/json")
public class TagController {
	private final TagService tagService;
	private final TagResourceAssembler tagAssembler;
	private final PagedResourcesAssembler<Tag> pageAssembler;

	public TagController(TagService tagService, TagResourceAssembler tagAssembler,
			PagedResourcesAssembler<Tag> pageAssembler) {
		this.tagService = tagService;
		this.tagAssembler = tagAssembler;
		this.pageAssembler = pageAssembler;
	}

	@GetMapping()
	public PagedResources<TagResource> browse(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "20") Integer size) {
		final Page<Tag> tagPage = this.tagService.browse(page, size);
		return this.pageAssembler.toResource(tagPage, this.tagAssembler);
	}

	@GetMapping("/find")
	public Resources<TagResource> find(@RequestParam String containing) {
		return new Resources<>(
				this.tagService.find(containing).stream().map(this.tagAssembler::toResource).collect(Collectors.toList()),
				linkTo(methodOn(TagController.class).find(containing)).withRel("find"));
	}
}
