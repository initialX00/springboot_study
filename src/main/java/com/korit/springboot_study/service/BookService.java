package com.korit.springboot_study.service;

import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.repository.BookRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public SuccessResponseDto<List<Book>> getBooks(Book book) throws NotFoundException {
        List<Book> foundBooks = bookRepository
                .findBooks(book)
                .orElseThrow(
                        () -> new NotFoundException(book.getBookName() + "은 존재하지 않는 이름입니다")
                );
        return new SuccessResponseDto<>(foundBooks);
    }

    @Transactional(rollbackFor = Exception.class)
    public SuccessResponseDto<Book> addBook(ReqAddBookDto reqAddBookDto) throws DuplicateKeyException {
        return new SuccessResponseDto<>(
            bookRepository
                    .saveBook(new Book(0, reqAddBookDto.getBookName()))
                    .orElseThrow()
        );
    }
}
