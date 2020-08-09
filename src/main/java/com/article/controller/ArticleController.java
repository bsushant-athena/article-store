package com.article.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.article.entity.Article;
import com.article.exception.ArticleException;
import com.article.service.ArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/articles")
@Api(value="createArticle API", description=" This  ArticleController contains all the operation about Article")

public class ArticleController {
	
	
	Logger log = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;
	
	private String topicName = "topic1";
	
	
	@PostMapping
	@ApiOperation(value = "store Article api")
	public ResponseEntity<Article>  createArticle(@RequestBody Article article) {
		
		template.send(topicName,article);
		
		log.info("Request is send to kafka with Article Json {{}} ",article);
		
		return new ResponseEntity<Article>(articleService.createArticle(article), HttpStatus.CREATED);
	}
	
	
	
	
	@PatchMapping("/{article_id}")
	public ResponseEntity <Article> updateResource(@RequestParam(value ="title" , required = true) String title, @PathVariable("article_id") String article_id) {
	 
		Article newArticle = articleService.updateArticle(title, article_id);
	 return new ResponseEntity <Article> (newArticle, HttpStatus.OK);
	}
	
	@GetMapping
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	
	public List<Article> getAll(){
		
		return articleService.findAllArticle();
	}
	
	@GetMapping("/{article_id}")
	@ResponseBody
	@ApiOperation(value = "Get Article api Using Id ")
	public Article getById(@PathVariable String article_id) throws ArticleException {
		return articleService.getById(article_id);
	}
	
	
	@DeleteMapping(value="/{article_id}")
	@ApiOperation(value = "Delete Article Using Id")
	public String deleteById(@PathVariable String article_id) {
		
		articleService.deleteArticle(article_id);
		return "Article is deleted with id "+article_id;
		
	}
}
