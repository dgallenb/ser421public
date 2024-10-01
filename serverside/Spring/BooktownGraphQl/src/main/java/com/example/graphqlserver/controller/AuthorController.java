package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddAuthorInput;
import com.example.graphqlserver.dto.output.AddAuthorPayload;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;
    private boolean hasLinked;

    private static AuthorController centralController;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
        hasLinked = false;

        centralController = this;
    }

    public static AuthorController getController() {
        return centralController;
    }

    private void link() {
        List<Author> authors = authorService.getAuthors();

        BookController bookController = BookController.getController();

        List<Book> books = bookController.books();

        for(Book book : books) { // this will slow everything down, particularly as the data expands
            for(Author author : authors) {
                if(author.getId() == book.getAuthorId()) {
                    book.setAuthor(author);
                    author.addBook(book);
                    break;
                }
            }
        }

        // Bug issue: for some reason, the linkages aren't persistent? So I have to remake them every time.
        //hasLinked = true;
    }


    @QueryMapping
    public List<Author> authors() {
        if(!hasLinked) {
            link();
        }

        return authorService.getAuthors();
    }

    @QueryMapping
    public  Author authorById(@Argument("id") int id) {
        if(!hasLinked) {
            link();
        }

        return authorService.getAuthorById(id);
    }

    @MutationMapping
    public AddAuthorPayload addAuthor(@Argument AddAuthorInput input) {
        if(!hasLinked) {
            link();
        }

        Author author = authorService.addAuthor(input.firstName(), input.lastName());
        var out = new AddAuthorPayload(author);
        return out;
    }
}
