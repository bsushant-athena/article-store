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

	@Value("${reading.speed.of.avg.human}")
	private int avgHumanReadingSpeed;

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

		//business logic
		//get total word count

		updateArticle.setWordcount (4000L);

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
		currentArticle.setSlug(title);

		//business logic
		currentArticle.setWordcount(102L);

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

		Article currentArticle = articleRepository.getById(slug_id);

		//business logic
		long wordcount = currentArticle.getWordcount();

		int totalHumanMinutes = (int)wordcount/avgHumanReadingSpeed;
		int days = totalHumanMinutes / (24 * 60);

		totalHumanMinutes = totalHumanMinutes % (24 * 60);
		int hours = totalHumanMinutes / 60;

		totalHumanMinutes %= 60;
		int minutes = totalHumanMinutes / 60 ;

		totalHumanMinutes %= 60;
		int seconds = totalHumanMinutes;


		ArticleReadTime articleReadTime = new ArticleReadTime();
		articleReadTime.setArticleId ( slug_id );

		TimetoRead timetoRead = new TimetoRead();
		timetoRead.setDays ( days );
		timetoRead.setHours ( hours );
		timetoRead.setMins( minutes );
		timetoRead.setSeconds ( seconds );

		articleReadTime.setTimetoRead ( timetoRead );

		return articleReadTime;
	}
}
