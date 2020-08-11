package com.article.controller;

import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ArticleControllerTest {
    @InjectMocks
    ArticleController articleController;

    @Mock
    com.article.service.ArticleService articleService;

    @org.mockito.Mock
    com.article.service.ArticleReadTimeService articleReadTimeService;

    @org.junit.jupiter.api.Test
    public void testCreateArticle() throws com.article.exception.ArticleException {
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setBody ( "hello" );
        article.setDescription ( "bye" );
        article.setTitle ( "ok cool" );
        when(articleService.createArticle ( article ))
                .thenReturn ( article ) ;
        org.springframework.http.ResponseEntity < com.article.entity.Article > createdArticleResponse = articleController.createArticle (article);
        assertEquals ( createdArticleResponse.getStatusCode (), org.springframework.http.HttpStatus.CREATED );
    }

    @org.junit.jupiter.api.Test
    public void testUpdateArticle() throws com.article.exception.ArticleException {
        String title = "new tile";
        long slug_id = 2L;
        com.article.entity.Article returnedArticle = new com.article.entity.Article ();
        returnedArticle.setSlug ( "new-title" );
        when(articleService.updateArticleTitle ( title , slug_id))
                .thenReturn ( returnedArticle ) ;
        org.springframework.http.ResponseEntity < com.article.entity.Article > updatedArticleResponse = articleController.updateArticle (title,slug_id);
        assertEquals ( updatedArticleResponse.getStatusCode (), org.springframework.http.HttpStatus.OK );
        assertEquals ( updatedArticleResponse.getBody ().getSlug (), "new-title" );
    }

    @org.junit.jupiter.api.Test
    public void testgetBySlugIdNullCase() {
        long slug_id = 2L;
        com.article.entity.Article returnedArticle = null;
        when(articleService.getBySlug_Id (slug_id))
                .thenReturn ( returnedArticle ) ;
        org.springframework.http.ResponseEntity < com.article.entity.Article > getArticleResponse = articleController.getBySlugId (slug_id);
        assertEquals ( getArticleResponse.getStatusCode (), org.springframework.http.HttpStatus.NOT_FOUND );
        assertNull ( getArticleResponse.getBody () );
    }

    @org.junit.jupiter.api.Test
    public void testgetBySlugIdValidCase() {
        long slug_id = 2L;
        com.article.entity.Article returnedArticle = new com.article.entity.Article ();
        returnedArticle.setTitle ( "title" );
        returnedArticle.setDescription ( "desc" );
        returnedArticle.setBody ( "body" );
        when(articleService.getBySlug_Id (slug_id))
                .thenReturn ( returnedArticle ) ;
        org.springframework.http.ResponseEntity < com.article.entity.Article > getArticleResponse = articleController.getBySlugId (slug_id);
        assertEquals ( getArticleResponse.getStatusCode (), org.springframework.http.HttpStatus.OK );
        assertNotNull ( getArticleResponse.getBody () );
    }

    @org.junit.jupiter.api.Test
    public void testDeleteArticleValidCase() {
        long slug_id = 2L;
        when(articleService.deleteArticle (slug_id))
                .thenReturn ( slug_id ) ;
        String deleteArticleResponse = articleController.deleteBySlugId (slug_id);
        assertEquals ( deleteArticleResponse, "Deleted article with id 2" );
        assertNotNull ( deleteArticleResponse );
    }

    @org.junit.jupiter.api.Test
    public void testDeleteArticleInvalidCase() {
        long slug_id = 2L;
        when(articleService.deleteArticle (slug_id))
                .thenReturn ( 0L ) ;
        String deleteArticleResponse = articleController.deleteBySlugId (slug_id);
        assertEquals ( deleteArticleResponse, "No valid slugId found!" );
        assertNotNull ( deleteArticleResponse );
    }

    @org.junit.jupiter.api.Test
    public void testGetTimetoReadNullCase() {
        long slug_id = 2L;
        when(articleReadTimeService.getTimetoRead (slug_id))
                .thenReturn ( null ) ;
        org.springframework.http.ResponseEntity < com.article.entity.ArticleReadTime > getArticleReadTimeResponse = articleController.getTimetoRead (slug_id);
        assertEquals ( getArticleReadTimeResponse.getStatusCode (), org.springframework.http.HttpStatus.NOT_FOUND );
        assertNull ( getArticleReadTimeResponse.getBody () );
    }

    @org.junit.jupiter.api.Test
    public void testGetTimetoReadNotNullCase() {
        long slug_id = 2L;
        com.article.entity.ArticleReadTime articleReadTime = new com.article.entity.ArticleReadTime ();
        com.article.entity.TimetoRead timetoRead = new com.article.entity.TimetoRead ();
        timetoRead.setMins ( 1 );
        articleReadTime.setTimetoRead ( timetoRead );
        articleReadTime.setArticleId ( slug_id );
        when(articleReadTimeService.getTimetoRead (slug_id))
                .thenReturn ( articleReadTime ) ;
        org.springframework.http.ResponseEntity < com.article.entity.ArticleReadTime > getArticleReadTimeResponse = articleController.getTimetoRead (slug_id);
        assertEquals ( getArticleReadTimeResponse.getStatusCode (), org.springframework.http.HttpStatus.OK );
        assertNotNull ( getArticleReadTimeResponse.getBody () );
    }
}
