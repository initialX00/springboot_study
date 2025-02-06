package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.request.ReqSearchBookDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/books")
    @ApiOperation(value = "책 조회")
    public ResponseEntity<SuccessResponseDto<List<Book>>> getBook(
            @RequestParam Book bookName
            ) throws NotFoundException {
        return ResponseEntity.ok().body(bookService.getBooks(bookName));
    }

    @PostMapping("/api/books")
    @ApiOperation(value = "책 추가")
    public ResponseEntity<SuccessResponseDto<Book>> addBook(
            @Validated @RequestBody ReqAddBookDto reqAddBookDto
    ) throws MethodArgumentNotValidException {
        boolean isNull = reqAddBookDto == null;
        boolean isEmpty = reqAddBookDto.getBookName().isBlank();
        boolean isNotNum = !Pattern.matches("^(?=.*[A-Za-z가-힣])(?=.*[0-9])[A-Za-z0-9가-힣]+$", reqAddBookDto.getBookName());

        if(isNull || isEmpty || isNotNum) {
            BindingResult bindingResult = new BeanPropertyBindingResult(null, "book");
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        return ResponseEntity.ok().body(bookService.addBook(reqAddBookDto));
    }
}
