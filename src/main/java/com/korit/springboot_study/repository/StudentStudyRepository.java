package com.korit.springboot_study.repository;

import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.mapper.StudentStudyMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StudentStudyRepository {

    @Autowired
    private StudentStudyMapper studentStudyMapper;


    public Optional<List<Major>> findMajorAll() {
        //List는 빈배열이라도 빈배열인것이지 null이 아니다.
        List<Major> foundMajors = studentStudyMapper.selectMajorsAll();

        if (foundMajors.isEmpty()) { //리스트가 빈배열일 경우
            return Optional.empty(); //옵션널을 null로 처리
        }

        return Optional.ofNullable(foundMajors);

        //return foundMajors.isEmpty() ? Optional.empty() : Optional.ofNullable(foundMajors);
    }

    public Optional<List<Instructor>> findInstructorAll() {
        List<Instructor> foundInstructors = studentStudyMapper.selectInstructorAll();

        return foundInstructors.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundInstructors);
    }


    public Optional<Major> saveMajor(Major major) throws DuplicateKeyException { //중복예외처리
        try {
            studentStudyMapper.insertMajor(major);
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("majorName", "이미 존재하는 학과명입니다.")
            );
        }
        return Optional.ofNullable(new Major(major.getMajorId(), major.getMajorName()));
    }

    public Optional<Instructor> saveInstructor(Instructor instructor) throws DuplicateKeyException {
        try {
            studentStudyMapper.insertInstructor(instructor);
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("instructorName", "이미 존재하는 교수입니다.")
            );
        }
        return Optional.ofNullable(new Instructor(instructor.getInstructorId(), instructor.getInstructorName()));
    }


    public Optional<Major> updateMajor(Major major) throws DuplicateKeyException {
        try {
            if(studentStudyMapper.updateMajorName(major) < 1) {
               return Optional.empty();
            }
        } catch(DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("majorName", "이미 존재하는 학과명입니다.")
            );
        }
        return Optional.ofNullable(major);
    }
}
