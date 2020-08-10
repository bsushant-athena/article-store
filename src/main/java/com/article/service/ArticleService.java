package com.article.service;

import com.article.entity.*;
import com.article.exception.*;
import com.article.repository.*;
import java.time.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	public com.article.entity.Article createArticle( Article article) {
		Article updateArticle = new Article();

		//set article uuid
		String uniqueID = UUID.randomUUID().toString();
		updateArticle.setSlug_id(uniqueID);

		//set basic article details
		String lowercaseSlug = article.getTitle().replace(" ", "-").toLowerCase();
		updateArticle.setSlug(lowercaseSlug);
		updateArticle.setTitle(article.getTitle());
		updateArticle.setDescription(article.getDescription());
		updateArticle.setBody(article.getBody());

		//set article timestamp
		ZonedDateTime zdtObj = ZonedDateTime.now();
		updateArticle.setCreatedAt(zdtObj);
		updateArticle.setUpdatedAt(zdtObj);
		
		return articleRepository.save(updateArticle);
	}

	public Article getById(String slug_id) throws ArticleException {
		Article article = articleRepository.getById(slug_id);
		try {
			if(article == null) {
				throw new ArticleException("No article found with id " + slug_id);
			}
		}catch (Exception exception) {
			throw new ArticleException("No article found with id " + slug_id);
		}
		return article;
	}
	
	public Article updateArticle(String title, String slug_id) {
		Article currentArticle = articleRepository.getById(slug_id);
		currentArticle.setTitle(title);
		return articleRepository.save(currentArticle);
	}

	public String deleteArticle(String slug_id) {
		if(slug_id.equals(articleRepository.isIdExist(slug_id))) {
			articleRepository.deleteByArticalId(slug_id);
		}else {
			slug_id = "ID DOES NOT EXISTS!";
		}
		return slug_id;
	}

	public ArticleReadTime getTimetoRead(String slug_id){
		return null;
	}
}
