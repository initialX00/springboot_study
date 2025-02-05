package com.korit.springboot_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/mvc/hello")
    public String hello(Model model) {
        model.addAttribute("name", "준일");
        System.out.println("hello 메소드 호출");
        return "hello"; //경로
        // 경로 및 확장명 생략 가능 "/resource/temlates/hello.html"
    }

    @GetMapping("/mvc/hello2")
    public String hello2() {
        System.out.println("hello 메소드 호출");
        return "hello";
    }
}
