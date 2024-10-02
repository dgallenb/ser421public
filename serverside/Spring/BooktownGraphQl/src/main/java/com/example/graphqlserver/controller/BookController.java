package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddBookInput;
import com.example.graphqlserver.dto.output.AddBookPayload;
import com.example.graphqlserver.dto.input.DeleteBookInput;
import com.example.graphqlserver.dto.output.DeleteBookPayload;
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
import java.util.ArrayList;

@Controller
public class BookController {

    private static BookController centralControl;

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;

        BookController.centralControl = this; // Trying to jury rig a singleton.
        
    }

    public static BookController getController() {
        return centralControl;
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

    @QueryMapping
    public List<Book> booksByAuthorId(@Argument("authorId") int authorId) { // NEW
        return bookService.getBooksByAuthorId(authorId);
    }

    @QueryMapping
    public List<String> bookTitlesByAuthorFirstName(@Argument("firstName") String firstName) { // NEW
        List<String> bookTitles = new ArrayList<String>();



        // 1. Get authors by first name
        List<Author> authors = AuthorController.getController().getAuthorsByFirstName(firstName);

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
    public DeleteBookPayload deleteBook(@Argument DeleteBookInput input) { // NEW
        Book b = bookService.getBookByISBN(input.isbn());
        
        if (b == null) {
            return new DeleteBookPayload(null);
        }

        AuthorController.getController().removeBookFromAuthor(b.getAuthorId(), input.isbn());


        String result = bookService.deleteBook(input.isbn());

        if(result.equals(input.isbn())) {
            return new DeleteBookPayload(result);
        }
        
        return new DeleteBookPayload(null);
    }
}
