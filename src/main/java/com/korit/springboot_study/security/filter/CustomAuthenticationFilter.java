package com.korit.springboot_study.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터 들어옴!!!");

        UserDetails principalUser = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "aaaa";
            }

            @Override
            public String getUsername() {
                return "aaaa";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principalUser, "", principalUser.getAuthorities()); //첫번쨰는 uername, 두번째는 password, 세번째 매개변수는 권한
        SecurityContextHolder.getContext().setAuthentication(authentication); //인증 처리  //기울어진 글자 static메서드
        chain.doFilter(request, response);
    }
}
