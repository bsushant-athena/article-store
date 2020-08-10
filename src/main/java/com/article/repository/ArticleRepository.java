package com.article.repository;

import com.article.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import javax.transaction.*;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

	@Transactional //spring proxy with TransactionInterceptor
	@Modifying //to define this is not a select query
	@Query("delete from Article article where article.slug_id=:slug_id")
	void deleteByArticalById(String slug_id);
}
