package com.article.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.entity.Article;
import com.article.exception.ArticleException;
import com.article.repository.ArticleRepository;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	
	public Article createArticle(Article article) {
		Article updateArticle = new Article();

		//set basic article details
		String lowercaseSlug = article.getTitle().replace(" ", "-").toLowerCase();
		updateArticle.setSlug(lowercaseSlug);
		updateArticle.setTitle(article.getTitle());
		updateArticle.setDescription(article.getDescription());
		updateArticle.setBody(article.getBody());

		//set article uuid
		String uniqueID = UUID.randomUUID().toString();
		updateArticle.setArticle_id(uniqueID);

		//set article timestamp
		ZonedDateTime zdtObj = ZonedDateTime.now();
		updateArticle.setCreatedAt(zdtObj);
		updateArticle.setUpdatedAt(zdtObj);
		
		return articleRepository.save(updateArticle);
	}
	
	public List<Article> findAllArticle(){
		return articleRepository.findAll();
	}

	public Article getById(String article_id) throws ArticleException {
		Article article = articleRepository.getById(article_id);
		try {
			if(article == null) {
				throw new ArticleException("No article found with id " + article_id);
			}

		}catch (Exception exception) {
			throw new ArticleException("No article found with id " + article_id);
		}
		return article;
	}

	
	public Article updateArticle(String title, String article_id) {
		Article currentArticle = articleRepository.getById(article_id);
		currentArticle.setTitle(title);
		return articleRepository.save(currentArticle);
	}

	public String deleteArticle(String article_id) {
		if(article_id.equals(articleRepository.isIdExist(article_id))) {
			articleRepository.deleteByArticalId(article_id);
		}else {
			article_id = "ID DOES NOT EXISTS!";
		}
		return article_id;
	}

}
