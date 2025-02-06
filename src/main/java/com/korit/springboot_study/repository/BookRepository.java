package com.korit.springboot_study.repository;

import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.mapper.BookMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepository {

    @Autowired
    private BookMapper bookMapper;


    public Optional<List<Book>> findBooks(Book book) throws NotFoundException {
        List<Book> foundBooks = bookMapper.selectBooks();  // book 매개변수를 사용해 책을 조회하는 예

        if (foundBooks.isEmpty()) {
            throw new NotFoundException(book.getBookName() + "은 존재하지 않는 이름입니다");
        }

        return Optional.of(foundBooks);
    }

    public Optional<Book> saveBook(Book book) throws DuplicateKeyException {
        try {
            bookMapper.insertBook(book);
        } catch(DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(), Map.of("bookName", "이미 존재하는 책입니다")
            );
        }
        return Optional.ofNullable(new Book(book.getBookId(), book.getBookName()));
    }
}
