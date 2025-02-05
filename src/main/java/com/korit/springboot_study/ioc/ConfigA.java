package com.korit.springboot_study.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigA {

    @Bean //메소드 명이 컴포넌트 명이된다
    public void call() {
        System.out.println("ConfigA call");
    }

    @Bean(value = "aaa") //컴포넌트 명 바꾸기
    public ClassD call2() {
        System.out.println("ConfigA call");
        return new ClassD();
        //컴포넌트를 사용하지 않고 직접 객체의 생성자를 생성할 때 2가지.
        //생성자를 통해서 매게변수를 달리 줄때, 라이브러리의 객체를 생성해서 bean으로 둘때 직접 생성자를 생성한다.
    }
}
