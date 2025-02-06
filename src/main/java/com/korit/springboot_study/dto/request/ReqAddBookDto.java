package com.korit.springboot_study.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqAddBookDto {
    @ApiModelProperty(value = "책 이름", example="난중일기", required = true)
    @Pattern(regexp = "^(?=.*[A-Za-z가-힣])(?=.*[0-9])[A-Za-z0-9가-힣]+$", message = "책 이름에는 특수문자를 넣을 수 없으며, 아무것도 안 적거나 숫자만 적을 수 없습니다.")
    private String bookName;
}
