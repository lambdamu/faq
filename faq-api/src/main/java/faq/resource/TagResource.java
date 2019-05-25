package faq.resource;

import org.springframework.hateoas.ResourceSupport;

import faq.model.Tag;


public class TagResource extends ResourceSupport {
	private final String name;
	
	public TagResource(Tag tag) {
		this.name = tag.getName();
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override 
	public boolean equals(Object o) {
		if (!super.equals(o)) {
			return false;
		}
		TagResource r = (TagResource) o;
		return this.name.equals(r.getName());
	}
}
