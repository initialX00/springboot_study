package com.korit.springboot_study.entity.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//mapper.xml이 필요로 하기에 다음 4가지 어노테이션이 반드시 필요하다.
@Data //#{}가 getter 역할을 하고, 매칭된 값을 대입하기 위해 setter가 필요
@NoArgsConstructor
@AllArgsConstructor //하나의 태그가 하나의 생성자이기에 AllArgsConstructorrk 필요
@Builder
public class Major {
    private int majorId;
    private String majorName;
}
