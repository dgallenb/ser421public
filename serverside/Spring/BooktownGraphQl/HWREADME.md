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
         updateAuthor(input: {authorId: 0, newName: "Robertaeeeeee"}) {
                        firstName
         }
     }

4. 
mutation {
         deleteBook(input: {isbn: "123456789"}) {
                        isbn
         }
     }
    

5. 

query {
        bookTitlesByAuthorFirstName(firstName: "Robert") 
    }
    