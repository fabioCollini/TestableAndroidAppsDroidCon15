package it.cosenonjaviste.testableandroidapps.model;

import java.util.Date;

public class Post {
    long id;
    Author author;
    String title;
    Date date;
    String url;
    String excerpt;

    public Post() {
    }

    public Post(long id, Author author, String title, Date date, String url, String excerpt) {
        this();
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.url = url;
        this.excerpt = excerpt;
    }

    public long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getExcerpt() {
        return excerpt;
    }
}
