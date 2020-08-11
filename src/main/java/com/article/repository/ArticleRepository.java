package com.article.repository;

import com.article.entity.*;
import org.springframework.stereotype.*;

@Repository
public interface ArticleRepository extends org.springframework.data.repository.CrudRepository<Article, Long> {
}
