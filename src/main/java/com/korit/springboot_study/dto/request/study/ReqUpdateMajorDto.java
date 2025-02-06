package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqUpdateMajorDto {
    @ApiModelProperty(value="학과명", example="컴퓨터공학과", required=true)
    @Pattern(regexp= "^[가-힣]+$", message = "학과명은 한글만 입력할 수 있으며, 공백, 특수문자, 영문, 숫자 입력이 불가합니다")
    private String majorName;
}
