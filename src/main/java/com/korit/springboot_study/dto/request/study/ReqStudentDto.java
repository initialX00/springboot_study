package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data //게터,세터,투스트링 등 자동작성
//@AllArgsConstructor 기본생성자를 오버로딩하여 필수값을 필요로하도록 초기화한다.
//@RequiredArgsConstructor final 또는 required=true이 있는 필수생성자를 생성한다.
@ApiModel(description = "학생정보 조회 학습 DTO")
public class ReqStudentDto {
    //id는 PathVariable로 처리하여 생략
    @NonNull //필수값으로 변경
    @ApiModelProperty(value = "학생 이름", example = "김준일", required = true)
    private String name;
    @ApiModelProperty(value = "학생 나이", example = "32", required = false)
    private int age;
}
