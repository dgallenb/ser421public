package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddAuthorInput;
import com.example.graphqlserver.dto.output.AddAuthorPayload;
import com.example.graphqlserver.model.Author;
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

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Author> authors() {
        return authorService.getAuthors();
    }

    @QueryMapping
    public  Author authorById(@Argument("id") int id) {
        return authorService.getAuthorById(id);
    }

    @MutationMapping
    public AddAuthorPayload addAuthor(@Argument AddAuthorInput input) {
        Author author = authorService.addAuthor(input.firstName(), input.lastName());
        var out = new AddAuthorPayload(author);
        return out;
    }
}
