package com.bookstore.controller.api;

import com.bookstore.dto.BookApiDto;
import com.bookstore.dto.UpdateBookDto;
import com.bookstore.dto.SearchParamsDto;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Validated
public class BookControllerApi {

    private final BookService bookService;

    public BookControllerApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/admin/api/books")
    public Page<BookApiDto> list(@Valid SearchParamsDto searchParams) {
        return bookService.findAll(searchParams);
    }

    @PostMapping("/admin/api/books/save")
    public Long save(@Valid @RequestBody BookApiDto book) {
        return bookService.save(book);
    }

    @PostMapping("/admin/api/books/update")
    public Long updatePrice(@Valid UpdateBookDto book) {
        return bookService.updatePrice(book);
    }

    @PostMapping("/admin/api/books/delete")
    public Long updatePrice(@RequestParam Long id) {
        return bookService.delete(id);
    }
}
