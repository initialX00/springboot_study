package com.korit.springboot_study.config;

import com.korit.springboot_study.security.exception.CustomAuthenticationEntryPoint;
import com.korit.springboot_study.security.filter.CustomAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //기본세팅은 이미 다 구현되있기에 오버라이드로 필요한것만 수정한다.
    //기본 id는 user, 기본 password는 서버실행 시 콘솔창에 나와있음

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable(); //알림창 로그인
        http.formLogin().disable(); //창화면 로그인

        //인터넷에 SecurityFilterChain 그림 참고하여 순서 확인하기
        http.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/api/post/**", "/api/user/**") //경로설정
                .permitAll() //허용
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }
}
