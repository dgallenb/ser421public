package com.example.graphqlserver.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.repository.BookRepository;

@Service
public class BookService {
	
	//Field Injection
	//@Autowired
	//private GroceryItemRepository groceryItemRepository;
	
	//Constructor Injection
	//generally recommended  
	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public List<Book> getBooks(){
		return bookRepository.findAll();
	}

	public Book getBookByISBN(String isbn) {
		List<Book> books = bookRepository.findAll();
		for(Book book : books) {
			if(book.getIsbn().equals(isbn)) {
				return book;
			}
		}
		return null;
	}

	public List<Book> getBooksByAuthorId(int id) {
		List<Book> books = bookRepository.findAll();
		List<Book> output = new ArrayList<Book>();
		for(Book book : books) {
			if(book.getAuthorId() == id) {
				output.add(book);
			}
		}
		return output;
	}
	
	public Book save(String isbn, String title, Author author) {
		Book book = new Book(isbn, title, author);
		bookRepository.save(book);
		return book;
	}
}
