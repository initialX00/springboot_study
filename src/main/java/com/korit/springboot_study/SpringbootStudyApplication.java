package com.korit.springboot_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootStudyApplication {
	//스프링부트에는 톰캣이 내장되어있다. main문을 통해 톰캣이 실행된다.
	public static void main(String[] args) {
		SpringApplication.run(SpringbootStudyApplication.class, args);
	}
	//스웨거는 http://localhost:8080/swagger-ui/index.html로 접속
}
