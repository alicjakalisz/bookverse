package com.bookverse.bookverse.controller;

import com.bookverse.bookverse.dto.ReviewDto;
import com.bookverse.bookverse.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> create(@Valid @RequestBody ReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable Long id, @Valid @RequestBody ReviewDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(reviewService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/reviews/search?title=harry
    @GetMapping("/search")
    public ResponseEntity<List<ReviewDto>> searchByUserName(@RequestParam String userName){
        return ResponseEntity.ok(reviewService.searchReviewsOfUser(userName));
    }
}
