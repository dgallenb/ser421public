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
                new Author(0, "Robert", "Frost"),
                new Author(1, "Martin", "Fowler"),
                new Author(2, "Kevin", "Gary")
    				
    		);
            System.out.println("DEBUGGING HERE: MADE IT TO AUTHOR INITIALIZER");
        	
        	authorRepository.saveAll(authors);
        };
    }

}
