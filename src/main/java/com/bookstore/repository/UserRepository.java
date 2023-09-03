package com.bookstore.repository;

import com.bookstore.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.function.Function;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
