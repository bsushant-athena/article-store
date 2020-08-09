package com.article.repository;

import com.article.entity.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import javax.transaction.*;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Transactional
	@Modifying
	@Query("delete from Article b where b.slug_id=:slug_id")
	void deleteByArticalId(String slug_id);

	@Query("select a from Article a where a.slug_id = :slug_id")
	Article getById(@Param("slug_id")  String slug_id);
	
	@Query("select a from Article a where a.slug_id = :slug_id")
	Object isIdExist(@Param("slug_id")  String slug_id);
}
