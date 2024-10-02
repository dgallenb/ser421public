package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddAuthorInput;
import com.example.graphqlserver.dto.output.AddAuthorPayload;
import com.example.graphqlserver.dto.output.UpdateAuthorPayload;
import com.example.graphqlserver.dto.input.UpdateAuthorInput;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.ArrayList;

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

    public List<Author> getAuthorsByFirstName(String firstName) { // NEW
        List<Author> output = new ArrayList<>();
        List<Author> authors = authors();
        for (Author author : authors) {
            if (author.getFirstName().equals(firstName)) {
                output.add(author);
            }
        }
        return output;
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

    @QueryMapping
    public List<Author> authorsByLastName(@Argument("lastName") String lastName) {
        if(!hasLinked) {
            link();
        }
        return authorService.getAuthorsByLastName(lastName);
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

    public Author removeBookFromAuthor(int id, String isbn) {
        return authorService.removeBookFromAuthor(id, isbn);
    }

    @MutationMapping
    public UpdateAuthorPayload updateAuthor(@Argument UpdateAuthorInput input) { // NEW

        String output = authorService.updateAuthor(input.authorId(), input.newName());
        return new UpdateAuthorPayload(output);
        /*
        var author = authorById(input.authorId());
        if(author != null) {
            String output = author.getFirstName();
            
            author.setFirstName(input.newName());

            return new UpdateAuthorPayload(output);
        }
        else {
            return null;
        }
        */
    }
}
