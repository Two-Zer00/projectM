package dev.twozer00.projectm.model;

import android.util.Log;

import java.io.Serializable;

public class Review implements Serializable {

    private String author;
    private Author_details author_details;
    private String content;
    private String created_at;
    private String id;
    private String updated_at;
    private String url;

    @Override
    public String toString() {
        return "Review{" +
                "author='" + author + '\'' +
                ", Author_details=" + author_details +
                ", content='" + content + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id='" + id + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Review() {
    }

    public Review(String author, Author_details author_details, String content, String created_at, String id, String updated_at, String url) {
        this.author = author;
        this.author_details = author_details;
        this.content = content;
        this.created_at = created_at;
        this.id = id;
        this.updated_at = updated_at;
        this.url = url;
    }

    public Author_details getAuthor_details() {
        return author_details;
    }

    public void setAuthor_details(Author_details author_details) {
        this.author_details = author_details;
    }
    // Getter Methods

    public String getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getId() {
        return id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getUrl() {
        return url;
    }

    // Setter Methods

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

