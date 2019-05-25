package faq.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import faq.controller.TagController;
import faq.model.Tag;

@Component
public class TagResourceAssembler extends ResourceAssemblerSupport<Tag, TagResource> {
	
	public TagResourceAssembler() {
		super(TagController.class, TagResource.class);
	}

	@Override
	public TagResource toResource(Tag tag) {
		return new TagResource(tag);
	}
}
