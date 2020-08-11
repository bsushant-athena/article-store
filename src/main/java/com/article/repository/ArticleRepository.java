package com.article.repository;

import com.article.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import javax.transaction.*;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

	@Transactional
	@Modifying
	@Query("delete from Article article where article.slug_id=:slug_id")
	void deleteBySlug_Id(@org.springframework.data.repository.query.Param ("slug_id") String slug_id);

	@Query("select article from Article article where article.slug_id = :slug_id")
	Object isIdExist(@org.springframework.data.repository.query.Param ("slug_id")  String slug_id);

	@Query("select article from Article article where article.slug_id = :slug_id")
	Optional<Article> getBySlug_Id(@org.springframework.data.repository.query.Param ("slug_id")  String slug_id);
}
