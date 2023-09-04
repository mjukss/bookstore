# Bookstore

## 1. Overview

The Bookstore application, is Java Spring Boot web application that provides access to a directory of books.
Additionally, it offers admin users special privileges for managing books. The application stores data in a PostgreSQL
database and uses in-memory caching to improve performance.

### Technology Stack:

    Java Spring Boot
    PostgreSQL
    Docker
    In-Memory Caching (Caffeine)
    ThymeLeaf

## 2. Getting Started

### Prerequisites:

    Unix based operating system
    Git
    Docker

### To run the application, follow these steps:

Clone the Git repository.

```bash
git clone https://github.com/mjukss/bookstore.git
```

Navigate to the root directory of the project:

```bash
cd bookstore
```

Start application by running docker-compose file:
This will start PostgreSQL container and Bookstore application

```bash
docker compose up
```

### Accessing the Application UI

Once the Docker containers are up and running, you can access the application locally:
```
http://localhost:80 or http://localhost
```

Alternatively you can use already deployed application:

```
http://135.181.157.42
```

### Accessing the Application API

Retrieve data:

```bash
curl "http://http://localhost:80/api/books?page=0&size=999&updatedAfter=2023-07-31T18:39:00.000Z&order=asc&sortBy=title"
```

Available parameters

    page: Integer
    size: Integer (book count per page)
    updatedAfter: Instant (example 2023-07-31T18:39:00.000Z)
    order: asc | desc
    sortBy: id | title | author | updatedAt | createdAt



## 3. Database Schema
 
### Users Table

The users table is used for user management and consists of the following columns:

    id BIGINT
    username VARCHAR
    password_hash VARCHAR

### Books Table

The books table stores book-related data and includes the following columns:

    id BIGINT
    title VARCHAR
    author VARCHAR
    price NUMERIC
    release_year VARCHAR
    created_at TIMESTAMP
    updated_at TIMESTAMP

## 4. Data Initialization

### Upon starting the PostgreSQL container, the following data is automatically initialized

An admin user with the username `admin` and password `password` is created.
The books table is populated with 60 initial book entries.

## 5. Key Features

### UI

The application provides a user-friendly interface for accessing and managing the book directory.

### API

Admin users can access api to retrieve books and additionally apply timestamp filtering and sorting result by id, title, author, release year, created at timestamp, updated at timestamp or price 

### Book Management

Admin users can update book prices add new books to the system.

### Caching

In-memory caching is implemented to optimize performance by reducing database queries.
Caches are invalidated on POST endpoints to ensure data consistency.
