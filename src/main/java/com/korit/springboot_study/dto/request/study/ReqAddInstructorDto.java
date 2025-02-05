package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqAddInstructorDto {
    @ApiModelProperty(value = "교수이름", example = "김이최", required = true)
    private String instructorName;
}
