package com.article.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@org.junit.jupiter.api.extension.ExtendWith ( org.mockito.junit.jupiter.MockitoExtension.class)
public class ArticleReadTimeServiceTest {

    @org.mockito.Mock
    private com.article.repository.ArticleRepository articleRepository;

    @org.mockito.InjectMocks
    private ArticleReadTimeService articleReadTimeService;

    @org.junit.jupiter.api.Test
    public void shouldReturnNullTimetoReadObject(){
        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.empty ());
        com.article.model.ArticleReadTime articleReadTime = articleReadTimeService.getTimetoRead ( 1 );
        assertNull(articleReadTime);
    }

    @org.junit.Test
    public void shouldReturnValidArticleTimetoReadObjectEqualWordCountCase(){
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        article.setSlug_id ( 1L );
        article.setWordcount ( 60L );
        article.setDescription ( "bye" );
        article.setTitle ( "ok" );
        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.of ( article ));
        com.article.model.ArticleReadTime articleReadTime = articleReadTimeService.getTimetoRead ( 1 );
        assertEquals ( articleReadTime.getArticleId (),1L );
        assertEquals ( articleReadTime.getTimetoRead ().getMins (),1L );
        assertEquals ( articleReadTime.getTimetoRead ().getSeconds (),0 );
    }

    @org.junit.Test
    public void shouldReturnValidArticleTimetoReadObjectLessWordCountCase(){
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        article.setSlug_id ( 1L );
        article.setWordcount ( 50L );
        article.setDescription ( "bye" );
        article.setTitle ( "ok" );
        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.of ( article ));
        com.article.model.ArticleReadTime articleReadTime = articleReadTimeService.getTimetoRead ( 1 );
        assertEquals ( articleReadTime.getArticleId (),1L );
        assertEquals ( articleReadTime.getTimetoRead ().getMins (),0 );
        assertEquals ( articleReadTime.getTimetoRead ().getSeconds (),50L );
    }

    @org.junit.Test
    public void shouldReturnValidArticleTimetoReadObjectLargeWordCountCase(){
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        article.setSlug_id ( 1L );
        article.setWordcount ( 121L );
        article.setDescription ( "bye" );
        article.setTitle ( "ok" );
        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.of ( article ));
        com.article.model.ArticleReadTime articleReadTime = articleReadTimeService.getTimetoRead ( 1 );
        assertEquals ( articleReadTime.getArticleId (),1L );
        assertEquals ( articleReadTime.getTimetoRead ().getMins (),2L );
        assertEquals ( articleReadTime.getTimetoRead ().getSeconds (),1L );
    }
}
