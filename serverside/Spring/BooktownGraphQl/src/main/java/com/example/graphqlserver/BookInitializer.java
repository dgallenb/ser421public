package com.example.graphqlserver;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.repository.BookRepository;

@Configuration
public class BookInitializer {
	
	@Bean
    CommandLineRunner bookCommandLineRunner(BookRepository bookRepository) {
        return args -> {
            // book initialization
        	List<Book> books = Arrays.asList(
    			new Book("123456789", "The Road Not Taken", 0),
                new Book("987654321", "To Kill a Mockingbird", 1),
                new Book("456789123", "The Great Gatsby", 2)
    		);
        	
        	bookRepository.saveAll(books);
        };
    }

}
