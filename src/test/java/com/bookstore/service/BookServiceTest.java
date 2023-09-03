package com.bookstore.service;

import com.bookstore.dto.SearchParamsDto;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Book book;


    @Test
    void findAll() {
        // given
        Page<Book> books = new PageImpl<Book>(List.of(book));
        SearchParamsDto searchParams = new SearchParamsDto(
                0,
                1,
                Instant.EPOCH,
                "title",
                "desc"
        );

        // when
        when(bookRepository.findAllUpdatedAfter(any(Instant.class), any(Pageable.class))).thenReturn(books);
        bookService.findAll(searchParams);

        // then
        verify(bookRepository, times(1)).findAllUpdatedAfter(any(Instant.class), any(Pageable.class));
    }


    @Test
    void findAll1() {
        // given
        Page<Book> books = new PageImpl<Book>(List.of(book));
        SearchParamsDto searchParams = new SearchParamsDto(
                0,
                1,
                null,
                "title",
                "desc"
        );

        // when
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(books);
        bookService.findAll(searchParams);

        // then
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
    }

}
