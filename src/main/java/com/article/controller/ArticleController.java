package com.article.controller;

import com.article.entity.*;
import com.article.exception.*;
import com.article.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleReadTimeService articleReadTimeService;

	@PostMapping
	public ResponseEntity<Article>  createArticle(@RequestBody Article article) throws ArticleException {
		return new ResponseEntity<Article>(articleService.createArticle(article), HttpStatus.CREATED);
	}
	
	@PatchMapping("/{slug_id}")
	public ResponseEntity <Article> updateArticle(@RequestParam(value ="title" , required = true) String title, @PathVariable("slug_id") String slug_id) throws ArticleException {
	 	Article newArticle = articleService.updateArticleTitle(title, slug_id);
	 	return new ResponseEntity <Article> (newArticle, HttpStatus.OK);
	}
	
	@GetMapping("/{slug_id}")
	@ResponseBody
	public Article getBySlugId( @PathVariable String slug_id) throws ArticleException {
		return articleService.getById(slug_id);
	}

	@DeleteMapping(value="/{slug_id}")
	public String deleteBySlugId(@PathVariable String slug_id) {
		articleService.deleteArticle(slug_id);
		return "Deleted article with id "+slug_id;
	}

	@GetMapping(value = "/readtime/{slug_id}")
	public ArticleReadTime getTimetoRead(@PathVariable("slug_id") String slug_id) {
		return articleReadTimeService.getTimetoRead(slug_id);
	}
}
