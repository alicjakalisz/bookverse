package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.AuthorDto;
import com.bookverse.bookverse.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authors")
@Tag(name = "Authors", description = "Endpoints for managing authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //get all, get, post , put, delete

    @GetMapping()
    @Operation(summary = "Get all authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){

        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an author by id")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new author")
    public ResponseEntity<AuthorDto> create(@Valid @RequestBody AuthorDto authorDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.saveAuthor(authorDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing author by id")
    public ResponseEntity<AuthorDto> update (@PathVariable Long id, @RequestBody AuthorDto authorDto){
        authorDto.setId(id);// not necessary
        return ResponseEntity.ok(authorService.update(authorDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author by id")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }




}
