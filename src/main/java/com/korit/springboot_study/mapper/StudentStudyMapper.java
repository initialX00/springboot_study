package com.korit.springboot_study.mapper;

import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentStudyMapper {
    //Student_study_mapper.xml의 함수 불러오기

    //단건 조회는 Major, 다건 조회는 List<Major>
    List<Major> selectMajorsAll();
    List<Instructor> selectInstructorAll();

    int insertMajor(Major major);

    int insertInstructor(Instructor instructor);
}
