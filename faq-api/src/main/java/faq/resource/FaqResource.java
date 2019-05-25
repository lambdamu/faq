package faq.resource;

import java.util.SortedSet;

import org.springframework.hateoas.ResourceSupport;

import faq.model.Faq;

public class FaqResource extends ResourceSupport {
	private final Long uid;
	private final String question;
	private final String answer;
	private final SortedSet<String> tagset;

	public FaqResource(Faq faq) {
		this.uid = faq.getId();
		this.question = faq.getQuestion();
		this.answer = faq.getAnswer();
		this.tagset = faq.getTagSet();
	}

	public Long getUid() {
		return this.uid;
	}

	public String getQuestion() {
		return this.question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public SortedSet<String> getTagset() {
		return this.tagset;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FaqResource) {
			FaqResource r = (FaqResource) o;
			return this.uid.equals(r.getUid());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.uid.hashCode();
	}

}
