package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddBookInput;
import com.example.graphqlserver.dto.output.AddBookPayload;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.service.AuthorService;
import com.example.graphqlserver.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Book> books() {
        return bookService.getBooks();
    }

    @QueryMapping
    public  Book bookById(@Argument("isbn") String isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @MutationMapping
    public AddBookPayload addBook(@Argument AddBookInput input) {
        Author author = authorService.getAuthorById(input.authorId());
        if (author == null) {
            throw  new IllegalArgumentException("Author with ID " + input.authorId() + "does not exist");
        }
        Book book = bookService.save(input.isbn(), input.title(), author);
        //author.getBooks().add(book);
        var out = new AddBookPayload(book);
        return out;
    }
}
