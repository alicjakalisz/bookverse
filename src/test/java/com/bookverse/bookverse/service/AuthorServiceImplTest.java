package com.bookverse.bookverse.service;

import com.bookverse.bookverse.dto.AuthorDto;
import com.bookverse.bookverse.mapper.AuthorMapper;
import com.bookverse.bookverse.model.Author;
import com.bookverse.bookverse.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorMapper authorMapper;

    //under test
    @InjectMocks
    private AuthorServiceImpl authorService;



    @Test
    void getAuthorById() {
        Long authorId = 1L;

        Author author = new Author(authorId, "Jane Austen", null);
        AuthorDto expectedDto = new AuthorDto(authorId, "Jane Austen");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(expectedDto);

        AuthorDto result = authorService.getAuthorById(authorId);

        assertEquals(expectedDto, result);

    }


@Test
void create_ShouldSaveAuthorAndReturnDto() {
    AuthorDto dto = new AuthorDto(null, "Mark Twain");
    Author entity = new Author(null, "Mark Twain", null);
    Author saved = new Author(1L, "Mark Twain", null);
    AuthorDto expected = new AuthorDto(1L, "Mark Twain");

    when(authorMapper.toEntity(dto)).thenReturn(entity);
    when(authorRepository.save(entity)).thenReturn(saved);
    when(authorMapper.toDto(saved)).thenReturn(expected);

    AuthorDto result = authorService.saveAuthor(dto);

    assertEquals(expected, result);
    verify(authorRepository).save(entity);
}

@Test
void getById_ShouldReturnDto_WhenAuthorExists() {
    Long authorId = 1L;
    Author author = new Author(authorId, "Jane Austen", null);
    AuthorDto expectedDto = new AuthorDto(authorId, "Jane Austen");

    when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
    when(authorMapper.toDto(author)).thenReturn(expectedDto);

    AuthorDto result = authorService.getAuthorById(authorId);

    assertEquals(expectedDto, result);
}

@Test
void getById_ShouldThrow_WhenAuthorNotFound() {
    Long authorId = 1L;
    when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> authorService.getAuthorById(authorId));
}

@Test
void delete_ShouldCallRepositoryDelete() {
    Long authorId = 1L;

    // Mock behavior
    when(authorRepository.existsById(authorId)).thenReturn(true);
    authorService.deleteAuthor(authorId);

    //In Mockito, verify(...) is used to check if a specific method was called, AND with what arguments, during your test.
    verify(authorRepository,times(1)).deleteById(authorId);
}
}