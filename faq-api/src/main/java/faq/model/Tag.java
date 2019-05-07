package faq.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.hateoas.Identifiable;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A tag has its name as natural key and it references a list
 * of tagged FAQs.
 */
@Entity
@NaturalIdCache
public class Tag implements Identifiable<Long>, Comparable<Tag> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
	@SequenceGenerator(name = "tag_generator", sequenceName = "tag_seq")
	private Long id;

	@NaturalId
	@Column(nullable = false, unique = true)
	@Size(min = 1, max = 64, message = "{tag.name.size}")
	private String name;

	@ManyToMany(mappedBy = "tags", fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<Faq> faqs = new HashSet<>();

	protected Tag() {
	}

	public Tag(String name) {
		Assert.notNull(name, "name cannot be null");
		this.name = name;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Faq> getFaqs() {
		return this.faqs;
	}

	public void setFaqs(Set<Faq> faqs) {
		this.faqs.clear();
		this.faqs.addAll(faqs);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Tag) {
			Tag that = (Tag) o;
			return Objects.equals(this.name, that.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public int compareTo(Tag o) {
		return this.name.compareTo(o.getName());
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.id).append("|").append(this.name).toString();
	}

}
