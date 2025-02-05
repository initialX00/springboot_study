package com.korit.springboot_study.ioc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClassB {
    @Qualifier(value = "c1") //변수명 맞춰주기, value 생략가능
    @Autowired(required = false) //bean필수 여부 설정 가능, 디폴트는 true
    private ClassC classC1; //변수명을 같게하면 같은 녀석만 주입 가능
    @Qualifier("c2")
    @Autowired
    private ClassC classC2;

    public void classCallB() {
        System.out.println("ClassB 메소드 호출");
    }
}
