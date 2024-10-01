package com.example.graphqlserver.model;

import jakarta.persistence.*;
import com.example.graphqlserver.model.Author;


@Entity
public class Book {
    @Id
    private String isbn;
    private String title;

    @ManyToOne
    private Author author;

    private int tempId;

    public Book() {
        this.isbn = "";
        this.title = "";
        this.tempId = -2;
    }

    public Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.tempId = author.getId();
    }

    public Book(String isbn, String title, int authorId) {
        this.isbn = isbn;
        this.title = title;
        this.tempId = authorId;
        
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author a) {
        this.author = a;
    }

    public int getAuthorId() {
        if(isLinked()) {
            return author.getId();
        }
        return tempId;
    }

    public void setAuthorId(int authorId) {
        if(isLinked()) {
            this.author.setId(authorId);
        }
        else {
            this.tempId = authorId;
        }
    }

    public boolean isLinked() {
        return (this.author != null);
    }
}
