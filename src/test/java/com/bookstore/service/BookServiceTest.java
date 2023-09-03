package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.SearchParamsDto;
import com.bookstore.dto.UpdateBookDto;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Page<Book> books;

    @Test
    void shouldConvertSearchParamsToPageable() {
        // given
        var params1 = "page=0&size=1&sortBy=title&order=desc&updatedAfter=2023-07-31T18:39:00.000Z";
        var params2 = "size=14&sortBy=price&order=asc";
        var params3 = "page=2&size=20&sortBy=updatedAt&order=desc";
        var params4 = "";

        // when
        var pageable1 = bookService.toPageable(searchParams(params1));
        var pageable2 = bookService.toPageable(searchParams(params2));
        var pageable3 = bookService.toPageable(searchParams(params3));
        var pageable4 = bookService.toPageable(searchParams(params4));

        // then
        assertEquals(pageable1.toString(), "Page request [number: 0, size 1, sort: title: DESC]");
        assertEquals(pageable2.toString(), "Page request [number: 0, size 14, sort: price: ASC]");
        assertEquals(pageable3.toString(), "Page request [number: 2, size 20, sort: updatedAt: DESC]");
        assertEquals(pageable4.toString(), "Page request [number: 0, size 50, sort: createdAt: DESC]");
    }

    @Test
    void shouldFindAllBooks() {
        // given
        var rawParams = "page=0&size=1&sortBy=title&order=desc";
        var params = searchParams(rawParams);
        var pageable = bookService.toPageable(params);

        // when
        when(bookRepository.findAll(pageable)).thenReturn(books);
        bookService.findAll(params);

        // then
        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookRepository, times(0)).findAllUpdatedAfter(params.updatedAfter(), pageable);
    }

    @Test
    void shouldFindAllBooksAfterGivenDate() {
        var rawParams = "page=0&size=1&sortBy=title&order=desc";
        var params = searchParams(rawParams);
        var pageable = bookService.toPageable(params);

        // when
        when(bookRepository.findAll(pageable)).thenReturn(books);
        bookService.findAll(params);

        // then
        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookRepository, times(0)).findAllUpdatedAfter(params.updatedAfter(), pageable);
    }


    @Test
    void shouldUpdatePrice() {
        // given
        var updateBook = new UpdateBookDto(1L, BigDecimal.ONE);
        var entity = new Book("Title", "Author", null, "2000", Instant.EPOCH, Instant.EPOCH);

        // when
        when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));
        bookService.updatePrice(updateBook);

        // then
        verify(bookRepository, times(1)).findById(1L);
        assertEquals(entity.getPrice(), BigDecimal.ONE);
    }

    @Test
    void shouldSaveBook() {
        // given
        var book = new BookDto(null, "Title", "Author", "2000", null, null, null);

        // when
        when(bookRepository.save(any(Book.class))).thenReturn(any(Book.class));
        bookService.save(book);

        // then
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    private SearchParamsDto searchParams(String rawParams) {
        var params = new HashMap<String, String>();
        Arrays.stream(rawParams.split("&"))
                .filter(s -> !s.isEmpty())
                .map(a -> a.split("="))
                .forEach(tuple -> params.put(tuple[0], tuple[1]));

        return new SearchParamsDto(
                Optional.ofNullable(params.get("page")).map(Integer::parseInt).orElse(null),
                Optional.ofNullable(params.get("size")).map(Integer::parseInt).orElse(null),
                Optional.ofNullable(params.get("updatedAfter")).map(Instant::parse).orElse(null),
                params.get("sortBy"),
                params.get("order")
        );
    }
}
