package com.example.graphqlserver.model;

public class Book {
    private String isbn;
    private String title;

    private int authorId;

    public Book(String isbn, String title, int authorId) {
        this.isbn = isbn;
        this.title = title;
        this.authorId = authorId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    // NEW
    // Note: I should technically override the class's hash definition if I'm overriding .equals(), but it's not worth bothering with on this assignment.
    public boolean equals(Object o) {
        if(o instanceof Book) {
            Book b = (Book) o;
            if(b.getIsbn().equals(this.getIsbn())) {
                return true;
            }
            return false;
        }
        return false;
    }
}
