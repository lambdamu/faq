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
	public TagResource toResource(Tag Tag) {
		TagResource r = new TagResource(Tag);
		// TODO: add relevant links as needed
		return r;
	}
}
