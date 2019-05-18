package faq.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import faq.controller.FaqController;
import faq.model.Faq;

@Component
public class FaqResourceAssembler extends ResourceAssemblerSupport<Faq, FaqResource> {
	
	public FaqResourceAssembler() {
		super(FaqController.class, FaqResource.class);
	}

	@Override
	public FaqResource toResource(Faq faq) {
		FaqResource r = new FaqResource(faq);
		r.add(linkTo(methodOn(FaqController.class).getFaq(faq.getId())).withSelfRel());
		return r;
	}
}
