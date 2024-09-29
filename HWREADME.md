Note: For HW2, I define books as being equivalent if they have identical ISBN's. 

1.
query {
        booksByAuthorId(authorId: 0) {
            isbn
            title
            authorId
        }
    }

2. 
query {
        authorsByLastName(lastName: "Frost") {
            id
            lastName
            firstName
            books {
                isbn
                title
            }
        }
    }

3. 
mutation {
         updateAuthor(input: {authorId: 0, newName: "Roberta"}) {
    					firstName
         }
     }

4. 
mutation {
         deleteBook(input: {isbn: "123456789"}) {
                        isbn
         }
     }

5. Note: the assignment asks for "Book titles", not "Books", so I return a list of strings.
query {
        bookTitlesByAuthorFirstName(firstName: "Robert") 
    }

