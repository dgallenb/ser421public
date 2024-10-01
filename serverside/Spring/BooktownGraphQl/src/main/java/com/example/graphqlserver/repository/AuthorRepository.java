package com.example.graphqlserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    
    /*
    public List<Author> getAuthors() {
        return findAll();
    }

    public Author getAuthorById(int id) {
        Author author = getReferenceById(id);

        if (author != null) {
            return author;
        }
        return null;
    }

    public Author save(String firstName, String lastName) {
        List<Book> book = new ArrayList<>();
        int nextId = findAll().isEmpty() ? 0 : findAll().get(findAll().size() - 1).getId() + 1;
        Author newAuthor = new Author(nextId, firstName, lastName, book);
        super.save(newAuthor, nextId);
        return newAuthor;
    }
    */
}
