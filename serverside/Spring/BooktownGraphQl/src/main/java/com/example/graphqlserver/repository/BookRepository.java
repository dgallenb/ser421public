package com.example.graphqlserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.graphqlserver.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    /*
    public List<Book> getBooks() {
        return findAll();
    }

    public Book getBookByISBN(String isbn) {
        List<Book> dummyBooks = super.findAll();
        for (Book book : dummyBooks) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public Book save(String isbn, String title, int authorId) {
        Book newBook = new Book(isbn, title, authorId);
        super.save(newBook, isbn);
        dummyBooks.add(newBook);
        return newBook;
    }

    public static ArrayList<Book> getBooksByAuthorId(int id) {
        ArrayList<Book> bookList = new ArrayList<>();
        List<Book> dummyBooks = super.findAll();
        for (Book book : dummyBooks) {
            if (book.getAuthorId() == id) {
                bookList.add(book);
            }
        }
        return bookList;
    }
    
    */
}
