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
	@Query("delete from Article b where b.article_id=:article_id")
	void deleteByArticalId(String article_id);

	@Query("select a from Article a where a.article_id = :article_id")
	Article getById(@Param("article_id")  String article_id);
	
	@Query("select a from Article a where a.article_id = :article_id")
	Object isIdExist(@Param("article_id")  String article_id);
}
