package com.article.controller;

import com.article.entity.*;
import com.article.exception.*;
import com.article.service.*;

import io.swagger.annotations.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@Api(value="article-store api", description="Provides basic article operations")
public class ArticleController {

	Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;

	@PostMapping
	@ApiOperation(value = "create article api")
	public ResponseEntity<Article>  createArticle(@RequestBody Article article) {
		return new ResponseEntity<Article>(articleService.createArticle(article), HttpStatus.CREATED);
	}
	
	@PatchMapping("/{slug_id}")
	public ResponseEntity <Article> updateArticle(@RequestParam(value ="title" , required = true) String title, @PathVariable("slug_id") String slug_id) {
	 	Article newArticle = articleService.updateArticle(title, slug_id);
	 	return new ResponseEntity <Article> (newArticle, HttpStatus.OK);
	}

//
//	@GetMapping
//	@ApiResponses(value = {
//	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
//	        @ApiResponse(code = 404, message = "Resource not found")
//	})
	
	@GetMapping("/{slug_id}")
	@ResponseBody
	@ApiOperation(value = "Get article by Slug Id ")
	public Article getBySlugId(@PathVariable String slug_id) throws ArticleException {
		return articleService.getById(slug_id);
	}

	@DeleteMapping(value="/{slug_id}")
	@ApiOperation(value = "Delete article by Slug Id")
	public String deleteBySlugId(@PathVariable String slug_id) {
		
		articleService.deleteArticle(slug_id);
		return "Deleted article with id "+slug_id;
		
	}
}
