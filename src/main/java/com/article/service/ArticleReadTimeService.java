package com.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ArticleReadTimeService {

    @Autowired
    private com.article.repository.ArticleRepository articleRepository;

    @Value ("${reading.speed.of.avg.human}")
    private int avgHumanReadingSpeedPerMinute;

    public com.article.entity.ArticleReadTime getTimetoRead( String slug_id){

        com.article.entity.Article currentArticle = articleRepository.getById(slug_id);

        //business logic to calculate days,hours,minutes and seconds to read an article
        long wordcount = currentArticle.getWordcount();

        int totalHumanMinutes = (int)wordcount/avgHumanReadingSpeedPerMinute;
        int days = totalHumanMinutes / (24 * 60);

        totalHumanMinutes = totalHumanMinutes % (24 * 60);
        int hours = totalHumanMinutes / 60;

        totalHumanMinutes %= 60;
        int minutes = totalHumanMinutes / 60 ;

        totalHumanMinutes %= 60;
        int seconds = totalHumanMinutes;


        com.article.entity.ArticleReadTime articleReadTime = new com.article.entity.ArticleReadTime ();
        articleReadTime.setArticleId ( slug_id );

        com.article.entity.TimetoRead timetoRead = new com.article.entity.TimetoRead ();
        timetoRead.setDays ( days );
        timetoRead.setHours ( hours );
        timetoRead.setMins( minutes );
        timetoRead.setSeconds ( seconds );

        articleReadTime.setTimetoRead ( timetoRead );

        return articleReadTime;
    }

}
