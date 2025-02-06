package com.korit.springboot_study.controller.advice;

import com.korit.springboot_study.dto.response.common.BadRequsetResponseDto;
import com.korit.springboot_study.dto.response.common.NotFoundResponseDto;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    //NotFoundException 예외가 뜨면 여기로 온다
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<NotFoundResponseDto<?>> notFound(NotFoundException e) {
        return ResponseEntity.status(404).body(new NotFoundResponseDto<>(e.getMessage()));
    }

    //notFound처럼 사용자 임의 예외가 아니라 데미터베이스에서의 에러이기에
    //커스텀아기 위해 중간에 낚아채주었다.
    @ExceptionHandler(value = CustomDuplicateKeyException.class)
    public ResponseEntity<BadRequsetResponseDto<?>> duplicateKey(CustomDuplicateKeyException e) {
        return ResponseEntity.status(400).body(new BadRequsetResponseDto<>(e.getErrors()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<BadRequsetResponseDto<?>> validation(ConstraintViolationException e) {
        return ResponseEntity.status(400).body(new BadRequsetResponseDto<>(
                e.getConstraintViolations()
                        .stream()
                        .map(constraintViolation -> Map.of(constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
                        .collect(Collectors.toList())
        ));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequsetResponseDto<?>> validation(MethodArgumentNotValidException e) {
        List<Map<String, String>> errorMap = null;
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            errorMap = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> Map.of(fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.status(400).body(new BadRequsetResponseDto<>(errorMap));
    }
}
