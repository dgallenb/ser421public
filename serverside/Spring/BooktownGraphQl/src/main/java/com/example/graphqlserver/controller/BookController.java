package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddBookInput;
import com.example.graphqlserver.dto.output.AddBookPayload;
import com.example.graphqlserver.dto.input.DeleteBookInput;
import com.example.graphqlserver.dto.output.DeleteBookPayload;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.repository.AuthorRepository;
import com.example.graphqlserver.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.ArrayList;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public List<Book> books() {
        return bookRepository.getBooks();
    }

    @QueryMapping
    public  Book bookByISBN(@Argument("isbn") String isbn) {
        return bookRepository.getBookByISBN(isbn);
    }

    @QueryMapping
    public List<Book> booksByAuthorId(@Argument("authorId") int authorId) { // NEW
        return bookRepository.getBooksByAuthorId(authorId);
    }

    @QueryMapping
    public List<String> bookTitlesByAuthorFirstName(@Argument("firstName") String firstName) { // NEW
        List<String> bookTitles = new ArrayList<String>();

        // 1. Get authors by first name
        List<Author> authors = authorRepository.getAuthorsByFirstName(firstName);

        // 2. Extract their books.
        for(Author author : authors) {
            List<Book> books = author.getBooks();

            for(Book book : books) {
                bookTitles.add(book.getTitle());
            }
        }

        return bookTitles;
    }

    @MutationMapping
    public AddBookPayload addBook(@Argument AddBookInput input) {
        Author author = authorRepository.getAuthorById(input.authorId());
        if (author == null) {
            throw  new IllegalArgumentException("Author with ID " + input.authorId() + "does not exist");
        }
        var book = bookRepository.save(input.isbn(), input.title(), input.authorId());
        author.getBooks().add(book);
        var out = new AddBookPayload(book);
        return out;
    }

    
    @MutationMapping
    public DeleteBookPayload deleteBook(@Argument DeleteBookInput input) { // NEW
        Book book = bookRepository.getBookByISBN(input.isbn());
        if (book == null) {
            return null;
        }

        Author author = authorRepository.getAuthorById(book.getAuthorId());
        String isbn = book.getIsbn();
        author.getBooks().remove(book);

        bookRepository.getBooks().remove(book);
        
        var out = new DeleteBookPayload(isbn);
        return out;
    }
}
