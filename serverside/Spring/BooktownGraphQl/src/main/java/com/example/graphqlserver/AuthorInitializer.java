package com.example.graphqlserver;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.repository.AuthorRepository;
import com.example.graphqlserver.service.BookService;

@Configuration
public class AuthorInitializer {
	
	@Bean
    CommandLineRunner groceryItemsCommandLineRunner(AuthorRepository authorRepository) {
        return args -> {
            // grocery items initialization
        	List<Author> authors = Arrays.asList(
                new Author(0, "Robert", "Frost", Arrays.asList(new Book("123456789", "The Road Not Taken", 0))),
                new Author(1, "Martin", "Fowler", Arrays.asList(new Book("987654321", "To Kill a Mockingbird", 1))),
                new Author(2, "Kevin", "Gary", Arrays.asList(new Book("456789123", "The Great Gatsby", 2)))
    				
    		);
        	
        	authorRepository.saveAll(authors);
        };
    }

}
