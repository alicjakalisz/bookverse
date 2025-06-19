package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.AuthorDto;
import com.bookverse.bookverse.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authors")

public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //get all, get, post , put, delete

    @GetMapping()
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){

        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDto> create(@Valid @RequestBody AuthorDto authorDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.saveAuthor(authorDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> update (@PathVariable Long id, @RequestBody AuthorDto authorDto){
        authorDto.setId(id);// not necessary
        return ResponseEntity.ok(authorService.update(authorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }




}
