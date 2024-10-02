package com.example.graphqlserver.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.repository.AuthorRepository;

@Service
public class AuthorService {
	
	//Field Injection
	//@Autowired
	//private GroceryItemRepository groceryItemRepository;
	
	//Constructor Injection
	//generally recommended  
	private final AuthorRepository authorRepository;
	
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}


	
	public List<Author> getAuthors(){
		return authorRepository.findAll();
	}

	public Author getAuthorById(int id) {
		List<Author> authors = authorRepository.findAll();
		for(Author a : authors) {
			if(a.getId() == id) {
				return a;
			}
		}
		return null;
	}

    public List<Author> getAuthorsByLastName(String lastName) {
    	List<Author> output = new ArrayList<Author>();
    	List<Author> authors = authorRepository.findAll();
		for(Author a : authors) {
			if(a.getLastName().equals(lastName)) {
				output.add(a);
			}
		}
        return output;
    }

	private int nextId() {
		List<Author> l = authorRepository.findAll();
		if(l != null) {
			return l.size();
		}
		// TODO: Check whether I need to build the repository if the list is null
		return 0;
	}
	
	public Author addAuthor(String firstName, String lastName) {
		Author a = new Author(nextId(), firstName, lastName);
		authorRepository.save(a);
		return a;
	}

	public Author removeBookFromAuthor(int id, String isbn) {
		Author a = getAuthorById(id);
		List<Book> books = a.getBooks();
		for(Book book : books) {
			if(book.getIsbn().equals(isbn)) {
				books.remove(book);
				break;
			}
		}
		return a;
	}

	public String updateAuthor(int id, String firstName) {
		Author a = authorRepository.getReferenceById(id);
		String output = a.getFirstName();
		a.setFirstName(firstName);
		authorRepository.saveAndFlush(a);
		return output;
	}
}
