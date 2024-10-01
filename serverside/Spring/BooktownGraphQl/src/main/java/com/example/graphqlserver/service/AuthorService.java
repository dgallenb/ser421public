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

	private int nextId() {
		List<Author> l = authorRepository.findAll();
		if(l != null) {
			return l.size();
		}
		// TODO: Check whether I need to build the repository if the list is null
		return 0;
	}
	
	public Author addAuthor(String firstName, String lastName) {
		Author a = new Author(nextId(),
					firstName,
					lastName,
					new ArrayList<Book>()
					);
		authorRepository.save(a);
		return a;
	}
}
