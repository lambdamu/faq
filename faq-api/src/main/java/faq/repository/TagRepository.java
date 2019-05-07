package faq.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import faq.model.Tag;

@RepositoryRestResource(collectionResourceRel = "tags", path = "tags")
public interface TagRepository extends JpaRepository<Tag, Long> {
	/**
	 * Native PostGreSQL ILIKE search set to <code>%name%</code>
	 * 
	 * @param name   string to search for
	 * @param pageable pagination request
	 * @return list of matching tags
	 */
	@Query(value = "SELECT t.* FROM tag t WHERE t.name ILIKE '%' || :name || '%'", 
      countQuery = "SELECT count(t.*) FROM tag t WHERE t.name ILIKE '%' || :name || '%'", nativeQuery = true)			 
	public List<Tag> findAllByNameContaining(@Param("name") String name, Pageable pageable);
	
	public Optional<Tag> findByName(String name);
}