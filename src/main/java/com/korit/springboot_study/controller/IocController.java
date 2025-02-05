package com.korit.springboot_study.controller;

import com.korit.springboot_study.ioc.ClassA;
import com.korit.springboot_study.ioc.ClassB;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//AllArgsConstructor
//@RequiredArgsConstructor //final 붙은 애들의 생성자 생성
public class IocController {

    @Autowired
    private ClassA a;
    @Autowired
    private ClassB b;

    //AllArgsConstructor
//    public IocController(ClassA a, ClassB b) {
//        this.a = a;
//        this.b = b;
//    }

    @GetMapping("/api/ioc")
    public ResponseEntity<?> call() {
//        ClassA a = new ClassA();
//        ClassB b = new ClassB();
        a.classCallA();
        b.classCallB();

        return ResponseEntity.ok(null);
    }
}
