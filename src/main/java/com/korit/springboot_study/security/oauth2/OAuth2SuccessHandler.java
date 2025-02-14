package com.korit.springboot_study.security.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${oauth2.client.redirect_url}")
    private String client_redirect_url;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        System.out.println(authentication);
//        System.out.println(authentication.getDetails());
//        System.out.println(authentication.getPrincipal());

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String oauth2Id = oAuth2User.getAttribute("oauth2Id");
        String provider = oAuth2User.getAttribute("provider");

        //프론트에게 요청하는거이기에 get요청만 가능
        response.sendRedirect(client_redirect_url + "?oauth2Id=" + oauth2Id + "&provider=" + provider);
    }
}
