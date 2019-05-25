package faq.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Identifiable;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A FAQ is composed of a question, an answer and a set of tags.
 */
@Entity
public class Faq implements Identifiable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_generator")
	@SequenceGenerator(name = "faq_generator", sequenceName = "faq_seq")
	private Long id;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "{faq.question.notBlank}")
	@Size(min = 1, max = 255, message = "{faq.question.size}")
	private String question;

	@Column(nullable = false)
	@NotBlank(message = "{faq.answer.notBlank}")
	@Size(min = 1, max = 255, message = "{faq.answer.size}")
	private String answer;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch=FetchType.LAZY)
	@JoinTable(name = "faq_tag", joinColumns = @JoinColumn(name = "faq_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@JsonIgnore
	private Set<Tag> tags = new HashSet<>();

	protected Faq() {
	}

	public Faq(String question, String answer) {
		Assert.notNull(question, "question cannot be null");
		Assert.notNull(answer, "answer cannot be null");
		this.question = question;
		this.answer = answer;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Set<Tag> getTags() {
		return this.tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags.clear();
		this.tags.addAll(tags);
	}

	public void addTag(Tag tag) {
		Assert.notNull(tag, "tag cannot be null");
		this.tags.add(tag);
		tag.getFaqs().add(this);
	}

	public boolean hasTag(Tag tag) {
		boolean found = false;
		for (Iterator<Tag> iterator = this.tags.iterator(); iterator.hasNext() && !found;) {
			found = iterator.next().equals(tag);
		}
		return found;
	}

	public void removeTag(Tag tag) {
		this.tags.remove(tag);
		tag.getFaqs().remove(this);
	}

	@Transient
	public SortedSet<String> getTagSet() {
		return this.tags.stream().map(Tag::getName)
				.collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Faq) {
			Faq that = (Faq) o;
			return Objects.equals(this.question, that.question);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.question.hashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.id)
				.append("|").append(this.question)
				.append("|").append(this.answer)
				.append("|").append(this.tags).toString();
	}

}
