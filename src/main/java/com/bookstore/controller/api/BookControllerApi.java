package com.bookstore.controller.api;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.SearchParamsDto;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Validated
public class BookControllerApi {

    private final BookService bookService;

    public BookControllerApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public Page<BookDto> list(@Valid SearchParamsDto searchParams) {
        return bookService.findAll(searchParams);
    }
}
