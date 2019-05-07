package faq.model;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The client FAQ view.
 * 
 * The client view is a simplified version of the Hibernate entity.
 * The tag collection will be compressed to a set of strings.
 * 
 * It is used for JSON de/serialization.
 * 
 */
public class ClientFaq implements Serializable {
	private static final long serialVersionUID = -8083073367349230351L;
	private String question;
	private String answer;
	private SortedSet<String> tagset = new TreeSet<>();
	
	public ClientFaq() {
	}
	
	public ClientFaq(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public SortedSet<String> getTagset() {
		return tagset;
	}
	
	public boolean addTag(String tag) {
		return this.tagset.add(tag);
	}

	public void setTagset(SortedSet<String> tagset) {
		this.tagset = tagset;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.question)
				.append("|").append(this.answer)
				.append("|").append(this.tagset).toString();
	}
}
