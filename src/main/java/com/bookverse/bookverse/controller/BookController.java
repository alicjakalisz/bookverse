package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.BookDto;
import com.bookverse.bookverse.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@Validated  // Enable validation on method parameters
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by id")
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto dto) {
        return ResponseEntity.status(201).body(bookService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book by id")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @Valid @RequestBody BookDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing book by id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //GET /api/books/search?title=harry
    //if you want to validate only String (like no Dto object)  you can use @Validated on top the of class or method.
    @GetMapping("/search")
    @Operation(summary = "Search books by title")
    public ResponseEntity<List<BookDto>> searchBooksByTitle(
            @RequestParam
            @NotBlank(message = "Title must not be blank")
            @Size(min = 2, message = "Title must be at least 2 characters")
            String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }
}
