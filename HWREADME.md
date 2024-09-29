Note: For HW2, I define books as being equivalent if they have identical ISBN's. 

query {
        booksByAuthorId(authorId: 0) {
            isbn
            title
            authorId
        }
    }


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



mutation {
         updateAuthor(input: {authorId: 0, newName: "Roberta"}) {
    					firstName
         }
     }

