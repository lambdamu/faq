package faq.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import faq.model.Faq;

@RepositoryRestResource(collectionResourceRel = "faqs", path = "faqs")
public interface FaqRepository extends JpaRepository<Faq, Long> {
	/**
	 * Native PostGreSQL full text search on question, answer and tags.
	 * 
	 * @param search   a natural language search string
	 * @param pageable pagination request
	 * @return list of FAQ items
	 */
	@Query(value = "WITH search(q) AS (SELECT plainto_tsquery(:search)) SELECT f.* FROM faq f, search s WHERE to_tsvector(f.question || ' ' || f.answer) @@ s.q OR f.id IN (SELECT ft.faq_id from faq_tag ft, tag t WHERE t.id = ft.tag_id AND to_tsvector(t.name) @@ s.q)", 
	  countQuery = "WITH search(q) AS (SELECT plainto_tsquery(:search)) SELECT count(f.*) FROM faq f, search s WHERE to_tsvector(f.question || ' ' || f.answer) @@ s.q OR f.id IN (SELECT ft.faq_id from faq_tag ft, tag t WHERE t.id = ft.tag_id AND to_tsvector(t.name) @@ s.q)", 
	  nativeQuery = true)
	public Page<Faq> search(@Param("search") String search, Pageable pageable);
	
	public Optional<Faq> findByQuestion(String question);
}