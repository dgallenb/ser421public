package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddAuthorInput;
//import com.example.graphqlserver.dto.input.UpdateFirstNameByAuthorIdInput;
import com.example.graphqlserver.dto.output.AddAuthorPayload;
import com.example.graphqlserver.dto.output.UpdateAuthorPayload;
import com.example.graphqlserver.dto.input.UpdateAuthorInput;
//import com.example.graphqlserver.dto.output.UpdateFirstNameByAuthorIdPayload;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.getAuthors();
    }

    @QueryMapping
    public  Author authorById(@Argument("id") int id) {
        return authorRepository.getAuthorById(id);
    }

    @QueryMapping
    public List<Author> authorsByLastName(@Argument("lastName") String lastName) {
        return authorRepository.getAuthorsByLastName(lastName);
    }

    @MutationMapping
    public AddAuthorPayload addAuthor(@Argument AddAuthorInput input) {
        var author = authorRepository.save(input.firstName(), input.lastName());
        var out = new AddAuthorPayload(author);
        return out;
    }

    
    @MutationMapping
    public UpdateAuthorPayload updateAuthor(@Argument UpdateAuthorInput input) { // NEW
        var author = authorById(input.authorId());
        if(author != null) {
            String output = author.getFirstName();
            
            author.setFirstName(input.newName());

            return new UpdateAuthorPayload(output);
        }
        else {
            return null;
        }
    }
}
