package com.korit.springboot_study.controller;

import com.korit.springboot_study.aspect.annotation.PrintParamsAop;
import com.korit.springboot_study.dto.request.ReqSigninDto;
import com.korit.springboot_study.dto.request.ReqSignupDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.User;
import com.korit.springboot_study.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "계정API")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PrintParamsAop
    @ApiOperation(value = "회원가입")
    @PostMapping("/api/auth/signup")
    public ResponseEntity<SuccessResponseDto<User>> signup(
            @Valid @RequestBody ReqSignupDto reqSignupDto) throws MethodArgumentNotValidException {
        return ResponseEntity.ok().body(new SuccessResponseDto<>(authenticationService.signup(reqSignupDto)));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/api/auth/signin")
    public ResponseEntity<SuccessResponseDto<String>> signin(
            @Valid @RequestBody ReqSigninDto reqSigninDto) throws MethodArgumentNotValidException {
        return ResponseEntity.ok().body(new SuccessResponseDto<>(authenticationService.signin(reqSigninDto)));
    }

}
