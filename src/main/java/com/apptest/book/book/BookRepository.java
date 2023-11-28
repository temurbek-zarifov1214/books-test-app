package com.apptest.book.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIdAndDeletedAtIsNull(Integer id);

    @Query("""
            select  b from Book as b
            """)
    List<Book> getAllBooks();
}
