package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.BookDto;
import com.bookverse.bookverse.service.BookService;
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
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto dto) {
        return ResponseEntity.status(201).body(bookService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @Valid @RequestBody BookDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //GET /api/books/search?title=harry
    //if you want to validate only String (like no Dto object)  you can use @Validated on top the of class or method.
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooksByTitle(
            @RequestParam
            @NotBlank(message = "Title must not be blank")
            @Size(min = 2, message = "Title must be at least 2 characters")
            String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }
}
