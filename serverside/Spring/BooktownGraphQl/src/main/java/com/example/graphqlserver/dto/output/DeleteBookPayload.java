package com.example.graphqlserver.dto.output;

import com.example.graphqlserver.model.Book;

public record DeleteBookPayload(String isbn) {
}
