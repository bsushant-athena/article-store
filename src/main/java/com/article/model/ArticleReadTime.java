package com.article.model;

public class ArticleReadTime {

    private long articleId;
    private TimetoRead timetoRead;

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public TimetoRead getTimetoRead() {
        return timetoRead;
    }

    public void setTimetoRead(TimetoRead timetoRead) {
        this.timetoRead = timetoRead;
    }
}