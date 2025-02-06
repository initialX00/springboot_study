package com.korit.springboot_study.mapper;


import com.korit.springboot_study.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> selectBooks();

    int insertBook(Book book);
}
