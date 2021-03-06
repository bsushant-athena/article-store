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
	public ResponseEntity <Article> updateArticleTitle(@RequestParam(value ="title" , required = true) String title,
												  @PathVariable("slug_id") long slug_id) throws ArticleException {
	 	Article updatedArticle = articleService.updateArticleTitle(title, slug_id);
	 	return new ResponseEntity <Article> (updatedArticle, HttpStatus.OK);
	}

	@PutMapping("/{slug_id}")
	public ResponseEntity <Article> updateArticle(@RequestBody Article article , @PathVariable("slug_id") long slug_id) throws ArticleException {
		Article updatedArticle = articleService.updateArticle(article,slug_id);
		return new ResponseEntity <Article> (updatedArticle, HttpStatus.OK);
	}
	
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity < java.util.List<Article>> getArticles(){
		java.util.List < com.article.entity.Article> articleList= articleService.getAllArticles();
		return articleList.isEmpty () ? new ResponseEntity < java.util.List < com.article.entity.Article> >(articleList, HttpStatus.NO_CONTENT)
				: new ResponseEntity < java.util.List < com.article.entity.Article> >(articleList,HttpStatus.OK);
	}

	@GetMapping("/{slug_id}")
	@ResponseBody
	public ResponseEntity <Article> getBySlugId( @PathVariable long slug_id){
		Article article = articleService.getBySlug_Id(slug_id);
		return article != null ?
				new ResponseEntity <Article> (article, HttpStatus.OK) :
				new ResponseEntity <> ( HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value="/{slug_id}")
	public String deleteBySlugId(@PathVariable long slug_id) {
		long deletedSlugId = articleService.deleteArticle(slug_id);
		return deletedSlugId == 0L ? "No valid slugId found!" : "Deleted article with id "+slug_id;
	}

	@GetMapping(value = "/readtime/{slug_id}")
	public ResponseEntity < com.article.model.ArticleReadTime > getTimetoRead( @PathVariable("slug_id") long slug_id) {
		com.article.model.ArticleReadTime articleReadTime = articleReadTimeService.getTimetoRead(slug_id);
		return articleReadTime != null ?
				new ResponseEntity < com.article.model.ArticleReadTime > (articleReadTime, HttpStatus.OK) :
				new ResponseEntity <> ( HttpStatus.NOT_FOUND);
	}
}
