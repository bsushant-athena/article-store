package com.article.service;

import com.article.entity.*;
import com.article.exception.*;
import com.article.repository.*;
import java.time.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;

	private static final Logger logger = LoggerFactory.getLogger( com.article.controller.ArticleController.class);

	public com.article.entity.Article createArticle( Article article) throws ArticleException {

		//condition mandatory article details
		Optional<String> articleTitle = Optional.ofNullable(article.getTitle()).filter(s -> !s.isEmpty());
		Optional<String> articleDesc = Optional.ofNullable(article.getDescription()).filter(s -> !s.isEmpty());
		Optional<String> articleBody = Optional.ofNullable(article.getBody()).filter(s -> !s.isEmpty());

		if(!articleTitle.isPresent () || !articleDesc.isPresent () || !articleBody.isPresent ()){
			throw new com.article.exception.ArticleException ( "Please provide all mandatory fields for creating an article!" );
		}

		Article updateArticle = new Article();
		updateArticle.setTitle(articleTitle.get ());
		updateArticle.setDescription(articleDesc.get ());
		updateArticle.setBody(articleBody.get ());

		String lowercaseSlug = article.getTitle().replace(" ", "-").toLowerCase();
		updateArticle.setSlug(lowercaseSlug);

		//set article uuid
		String uniqueID = UUID.randomUUID().toString();
		updateArticle.setSlug_id(uniqueID);

		//business logic
		long wordcount = calculateTotalWordCount(article.getTitle(),article.getDescription(),article.getBody());
		updateArticle.setWordcount (wordcount);

		//set article timestamp
		ZonedDateTime zdtObj = ZonedDateTime.now();
		zdtObj.format( java.time.format.DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()) );
		updateArticle.setCreatedAt(zdtObj);
		updateArticle.setUpdatedAt(zdtObj);
		
		return articleRepository.save(updateArticle);
	}

	public Article getBySlug_Id(String slug_id) {
		Optional<Article> article = articleRepository.getBySlug_Id(slug_id);
		return article.isPresent() ? article.get() : null;
	}
	
	public Article updateArticleTitle(String title, String slug_id) throws ArticleException {
		Optional<Article> currentArticle = articleRepository.getBySlug_Id(slug_id);
		if(!currentArticle.isPresent ()) {
			throw new ArticleException("No article found with id " + slug_id);
		}
		String lowercaseSlug = title.replace(" ", "-").toLowerCase();
		currentArticle.get().setSlug(lowercaseSlug);
		//here not updating word count assuming title update will not cause huge diff in human reading time
		return articleRepository.save(currentArticle.get ()); //spring JPA CrudRepository inbuilt method
	}

	public String deleteArticle(String slug_id) {
		if(slug_id.equals(articleRepository.isIdExist(slug_id))) {
			articleRepository.deleteBySlug_Id(slug_id);
		}else {
			slug_id = "Slug ID DOES NOT EXISTS!";
		}
		return slug_id;
	}

	private long calculateTotalWordCount(String title, String description, String body){

		long totalWordsCount = Arrays.stream(title.split( " " )).count ();
		totalWordsCount  += Arrays.stream(description.split( " " )).count ();
		totalWordsCount += Arrays.stream(body.split( " " )).count ();
		return totalWordsCount;
	}
}
