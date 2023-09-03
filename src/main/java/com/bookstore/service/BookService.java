package com.bookstore.service;

import com.bookstore.dto.BookApiDto;
import com.bookstore.dto.BookViewDto;
import com.bookstore.dto.SearchParamsDto;
import com.bookstore.dto.UpdateBookDto;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable("books")
    public Page<BookApiDto> findAll(SearchParamsDto searchParams) {
        String sortBy = Optional.ofNullable(searchParams.sortBy()).orElse("createdAt");
        Sort.Direction order = Objects.equals(searchParams.order(), "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(order, sortBy);

        Pageable pageable = PageRequest.of(
                Optional.ofNullable(searchParams.page()).orElse(0),
                Optional.ofNullable(searchParams.size()).orElse(50)
        ).withSort(sort);

//        var foo = searchParams.updatedAfter() == null ? bookRepository.findAll(pageable) : bookRepository.findAllUpdatedAfter(searchParams.updatedAfter(), pageable);

        var books = Optional.ofNullable(searchParams.updatedAfter())
                .map(after -> bookRepository.findAllUpdatedAfter(after, pageable))
                .orElseGet(() -> bookRepository.findAll(pageable));


        return books.map(
                book -> new BookApiDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getReleaseYear(),
                        book.getCreatedAt(),
                        book.getUpdatedAt(),
                        book.getPrice()
                )
        );
    }

    @CacheEvict(value = "books", allEntries = true)
    public Long updatePrice(UpdateBookDto book) {
        Book entity = bookRepository.findById(book.id()).orElseThrow();
        entity.setPrice(book.price());
        entity.setUpdatedAt(Instant.now());

        return bookRepository.save(entity).getId();
    }

    @CacheEvict(value = "books", allEntries = true)
    public Long save(BookApiDto book) {
        Book entity = new Book(book.title(), book.author(), book.price(), book.releaseYear(), Instant.now(), Instant.now());

        return bookRepository.save(entity).getId();
    }

    @CacheEvict(value = "books", allEntries = true)
    public Long save(BookViewDto book) {
        Book entity = new Book(book.title(), book.author(), book.price(), book.releaseYear(), Instant.now(), Instant.now());

        return bookRepository.save(entity).getId();
    }

    @CacheEvict(value = "books", allEntries = true)
    public Long delete(Long id) {
        bookRepository.delete(bookRepository.findById(id).orElseThrow());

        return id;
    }

    @CacheEvict(value = "books", allEntries = true)
    public List<Long> saveAll(List<BookApiDto> books) {
        return books.stream().map(book -> {
                    Book entity = new Book(book.title(), book.author(), book.price(), book.releaseYear(), Instant.now(), Instant.now());
                    return bookRepository.save(entity).getId();
                }
        ).toList();
    }

    public void addToModel(Model model, Page<BookViewDto> books, SearchParamsDto searchParams) {
        var page = Optional.ofNullable(searchParams.page()).orElse(0);
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var authed = Optional.ofNullable(auth).filter(a -> !Objects.equals(a.getName(), "anonymousUser")).isPresent();

        model.addAttribute("authed", authed);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("pages", books.getTotalPages());
        model.addAttribute("bookCount", books.getTotalElements());
        model.addAttribute("page", page);
        model.addAttribute("books", books);
    }
}
