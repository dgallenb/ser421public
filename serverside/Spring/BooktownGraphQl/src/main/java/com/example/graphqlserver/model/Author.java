package com.example.graphqlserver.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    @Id
    private int id;
    private String firstName;
    private final String lastName;

    @OneToMany
    private List<Book> books = new ArrayList<>();

    public Author() {
        this.id = -1;
        this.firstName = "Dummy";
        this.lastName = "Dummy";
        this.books = new ArrayList<Book>();

    }

    public Author(int id, String firstName, String lastName) { //, List<Book> books
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = new ArrayList<Book>();
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    
    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book b) {
        if (books == null) {
            this.books = new ArrayList<Book>();
        }
        books.add(b);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    

}
