package com.korit.springboot_study.config;

import com.korit.springboot_study.security.exception.CustomAuthenticationEntryPoint;
import com.korit.springboot_study.security.filter.CustomAuthenticationFilter;
import com.korit.springboot_study.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //기본세팅은 이미 다 구현되있기에 오버라이드로 필요한것만 수정한다.
    //기본 id는 user, 기본 password는 서버실행 시 콘솔창에 나와있음

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //서버의 요청을 검증, restapi는 csrf토큰을 사용하지 않는다
        http.httpBasic().disable(); //알림창 로그인
        http.formLogin().disable(); //창화면 로그인

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션 비사용(서버저장 비사용)

        //인터넷에 SecurityFilterChain 그림 참고하여 순서 확인하기
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling() //예외처리 사용자가 처리(이 경우 예외란 인증 안된 사용자의 접근을 말한다)
                        .authenticationEntryPoint(customAuthenticationEntryPoint); //인증실패에 대한 처리

        http.authorizeRequests()
                .antMatchers("/api/auth/**") //경로설정
                .permitAll() //허용
                .antMatchers(
                        "/swagger-ui/**",
                        "/v2/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**"
                ) // 따로 적어도 되고 같이 적어도 된다."/api/auth/**","/swagger-ui/**"
                .permitAll()
                .anyRequest() //위 설정 이외에는 아래 설정들 적용
                .authenticated(); //인증된 사용자만 접근
//                .and()
//                .exceptionHandling() //예외처리 사용자가 처리(이 경우 예외란 인증 안된 사용자의 접근을 말한다)
//                .authenticationEntryPoint(customAuthenticationEntryPoint); //인증실패에 대한 처리
    }
}
