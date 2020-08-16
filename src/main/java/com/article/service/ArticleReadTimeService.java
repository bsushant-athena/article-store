package com.article.service;

import com.article.entity.*;
import com.article.model.*;
import com.article.repository.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
@Service
public class ArticleReadTimeService {

    @Autowired
    private ArticleRepository articleRepository;

    @Value ("${reading.speed.of.avg.human}")
    private int avgHumanReadingSpeedPerMinute;

    public ArticleReadTime getTimetoRead( long slug_id) {

        Optional<Article> currentArticle = articleRepository.findById(slug_id);

        if(!currentArticle.isPresent ()) {
            return null;
        }

        //business logic to calculate minutes and seconds to read an article
        int minutes=0,seconds=0;
        long wordcount = currentArticle.get().getWordcount();

        if(wordcount == avgHumanReadingSpeedPerMinute){

            minutes = 1;
            seconds = 0;
        }else if(wordcount < avgHumanReadingSpeedPerMinute){

            seconds = (int) ((wordcount * 60) / avgHumanReadingSpeedPerMinute);
            minutes = 0;
        }
        else{
            minutes = (int)wordcount/avgHumanReadingSpeedPerMinute;
            seconds = (int)wordcount % avgHumanReadingSpeedPerMinute;
        }


        ArticleReadTime articleReadTime = new ArticleReadTime ();
        articleReadTime.setArticleId ( slug_id );

        TimetoRead timetoRead = new TimetoRead ();
        timetoRead.setMins( minutes );
        timetoRead.setSeconds ( seconds );

        articleReadTime.setTimetoRead ( timetoRead );

        return articleReadTime;
    }

}
