package com.article.controller;

import com.article.entity.*;
import com.article.exception.*;
import com.article.model.*;
import com.article.service.*;
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
    ArticleService articleService;

    @org.mockito.Mock
    ArticleReadTimeService articleReadTimeService;

    @org.junit.jupiter.api.Test
    public void testCreateArticle() throws ArticleException {
        Article article = new Article ();
        article.setBody ( "hello" );
        article.setDescription ( "bye" );
        article.setTitle ( "ok cool" );
        when(articleService.createArticle ( article ))
                .thenReturn ( article ) ;
        org.springframework.http.ResponseEntity < Article > createdArticleResponse = articleController.createArticle (article);
        assertEquals ( createdArticleResponse.getStatusCode (), org.springframework.http.HttpStatus.CREATED );
    }

    @org.junit.jupiter.api.Test
    public void testUpdateArticleTitle() throws com.article.exception.ArticleException {
        String title = "new title";
        long slug_id = 2L;
        com.article.entity.Article returnedArticle = new com.article.entity.Article ();
        returnedArticle.setSlug ( "new-title" );
        when(articleService.updateArticleTitle ( title , slug_id))
                .thenReturn ( returnedArticle ) ;
        org.springframework.http.ResponseEntity < com.article.entity.Article > updatedArticleResponse = articleController.updateArticleTitle (title,slug_id);
        assertEquals ( updatedArticleResponse.getStatusCode (), org.springframework.http.HttpStatus.OK );
        assertEquals ( updatedArticleResponse.getBody ().getSlug (), "new-title" );
    }

    @org.junit.jupiter.api.Test
    public void testUpdateArticle() throws com.article.exception.ArticleException {
        long slug_id = 2L;
        Article updatedArticle = new Article ();
        updatedArticle.setSlug ( "new-title" );

        Article returnedArticle = new Article ();
        returnedArticle.setTitle ( "new title" );
        returnedArticle.setBody ( "body" );
        returnedArticle.setDescription ( "desc" );
        returnedArticle.setSlug ( "new-title" );
        returnedArticle.setWordcount ( 4L );

        when(articleService.updateArticle (updatedArticle,slug_id))
                .thenReturn ( returnedArticle );
        org.springframework.http.ResponseEntity < Article > updatedArticleResponse = articleController.updateArticle (updatedArticle,slug_id);
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
        org.springframework.http.ResponseEntity < ArticleReadTime > getArticleReadTimeResponse = articleController.getTimetoRead (slug_id);
        assertEquals ( getArticleReadTimeResponse.getStatusCode (), org.springframework.http.HttpStatus.NOT_FOUND );
        assertNull ( getArticleReadTimeResponse.getBody () );
    }

    @org.junit.jupiter.api.Test
    public void testGetTimetoReadNotNullCase() {
        long slug_id = 2L;
        ArticleReadTime articleReadTime = new ArticleReadTime ();
        TimetoRead timetoRead = new TimetoRead ();
        timetoRead.setMins ( 1 );
        articleReadTime.setTimetoRead ( timetoRead );
        articleReadTime.setArticleId ( slug_id );
        when(articleReadTimeService.getTimetoRead (slug_id))
                .thenReturn ( articleReadTime ) ;
        org.springframework.http.ResponseEntity < ArticleReadTime > getArticleReadTimeResponse = articleController.getTimetoRead (slug_id);
        assertEquals ( getArticleReadTimeResponse.getStatusCode (), org.springframework.http.HttpStatus.OK );
        assertNotNull ( getArticleReadTimeResponse.getBody () );
    }
}
