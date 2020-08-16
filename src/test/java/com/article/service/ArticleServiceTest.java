package com.article.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@org.junit.jupiter.api.extension.ExtendWith ( org.mockito.junit.jupiter.MockitoExtension.class)
public class ArticleServiceTest {

    @org.mockito.Mock
    private com.article.repository.ArticleRepository articleRepository;

    @org.mockito.InjectMocks
    private ArticleService articleService;

    @org.junit.jupiter.api.Test
    public void shouldReturnNullForNonExistentArticle(){
        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.empty ());
        com.article.entity.Article article = articleService.getBySlug_Id ( 1 );
        assertNull(article);
    }

    @org.junit.Test
    public void shouldReturnValidArticleObject(){
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        article.setSlug_id ( 1L );
        article.setWordcount ( 60L );
        article.setDescription ( "bye" );
        article.setTitle ( "ok" );

        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.of ( article ));
        com.article.entity.Article articleNew = articleService.getBySlug_Id ( 1L );

        assertEquals ( (long)articleNew.getSlug_id (),1L );
        assertEquals ( (long)articleNew.getWordcount (),60L );
        assertEquals ( articleNew.getDescription (),"bye");
        assertEquals ( articleNew.getTitle (),"ok");
        assertEquals ( articleNew.getSlug (),"hello");
    }

    @org.junit.Test
    public void shouldReturnValidUpdatedArticleObject() throws Exception{
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setBody ( "hello" );
        article.setSlug_id ( 1L );
        article.setDescription ( "bye bye" );
        article.setTitle ( "ok" );

        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.of ( article ));
        com.article.entity.Article articleNew = articleService.updateArticle ( article,1L );

        assertEquals ( (long)articleNew.getSlug_id (),1L );
        assertEquals ( articleNew.getDescription (),"bye bye");
        assertEquals ( articleNew.getTitle (),"ok");
        assertEquals ( articleNew.getSlug (),"hello");
    }

    @org.junit.Test
    public void shouldReturnZeroSlugIdForNonExistingArticle(){
        when(articleRepository.existsById ( 1L )).thenReturn( false);
        long deletedSlugId = articleService.deleteArticle ( 1 );
        assertEquals (deletedSlugId , 0L);
    }

    @org.junit.Test
    public void shouldReturnValidSlugIdForDeletedArticle(){
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        article.setSlug_id ( 1L );
        article.setWordcount ( 60L );
        article.setDescription ( "bye" );
        article.setTitle ( "ok" );

        when(articleRepository.existsById ( 1L )).thenReturn( true);
        long deletedSlugId = articleService.deleteArticle ( 1L );
        assertEquals ( deletedSlugId,1L );
    }

    @org.junit.Test(expected = com.article.exception.ArticleException.class)
    public void shouldThrowExceptionNonExistingArticle(){
        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.empty ( ));
    }

    @org.junit.Test
    public void shouldUpdateArticleTitle() throws com.article.exception.ArticleException {
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        article.setSlug_id ( 1L );
        article.setWordcount ( 60L );
        article.setDescription ( "bye" );
        article.setTitle ( "ok" );

        when(articleRepository.findById ( 1L )).thenReturn( java.util.Optional.of ( article));
        com.article.entity.Article updatedArticle = articleService.updateArticleTitle ( "ok Tata",1L );
        assertEquals ( updatedArticle.getSlug (),"ok-tata" );
    }

    @org.junit.Test(expected = com.article.exception.ArticleException.class)
    public void shouldThrowExceptionForNotProvidingMandatoryArticleFields() throws com.article.exception.ArticleException {
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        when(articleService.createArticle ( article )).thenReturn(article);
    }

    @org.junit.Test
    public void shouldCreateNewArticle()throws com.article.exception.ArticleException {
        com.article.entity.Article article = new com.article.entity.Article ();
        article.setSlug ( "hello" );
        article.setDescription ( "bye" );
        article.setTitle ( "ok cool" );

        com.article.entity.Article updatedArticle = articleService.createArticle (article);
        assertEquals ( updatedArticle.getSlug (),"ok-cool" );
        assertEquals ( (long)updatedArticle.getWordcount (), 4L);
        assertEquals ( (long)updatedArticle.getSlug_id (),1L);
        assertEquals ( updatedArticle.getDescription (),"bye");
    }
}
