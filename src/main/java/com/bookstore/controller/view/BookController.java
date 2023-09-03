package com.bookstore.controller.view;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.SearchParamsDto;
import com.bookstore.dto.UpdateBookDto;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(@Valid SearchParamsDto searchParams, Model model) {
        var books = bookService.findAll(searchParams);

        bookService.buildModel(model, books, searchParams);

        return "index";
    }

    @PostMapping(path = "/admin/books/update")
    public String updatePrice(@Valid UpdateBookDto book) {
        bookService.updatePrice(book);

        return "redirect:/";
    }

    @PostMapping(path = "/admin/books/add")
    public String addBook(@Valid BookDto book) {
        bookService.save(book);

        return "redirect:/";
    }
}
