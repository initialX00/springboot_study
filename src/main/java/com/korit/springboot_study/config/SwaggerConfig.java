package com.korit.springboot_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//스웨거 라이브러리 적용
//포스트맨에서 하던 기능을 실용적이게 문서화
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //해당 패키지 안에 있는 모든 컨트롤러 스웨거 적용
                .apis(RequestHandlerSelectors.basePackage("com.korit.springboot_study.controller"))
                //모든 URL 스웨거 적용
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("API 문서 제목")
                .description("API 문서 설명")
                .version("1.0")
                .contact(new Contact("관리자이름", "주소", "이메일"))
                .build();
    }

    // http://localhost:8080/swagger-ui/index.html 로 접속
    // 라이브러리의 주소를 들어가면 json파일이 나오고 이걸 포스트맨에 임포트하여 연동,이식할 수 있다.
}
