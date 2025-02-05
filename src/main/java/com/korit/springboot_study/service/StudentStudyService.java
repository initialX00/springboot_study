package com.korit.springboot_study.service;

import com.korit.springboot_study.dto.request.study.ReqAddInstructorDto;
import com.korit.springboot_study.dto.request.study.ReqAddMajorDto;
import com.korit.springboot_study.dto.response.common.NotFoundResponseDto;
import com.korit.springboot_study.dto.response.common.ResponseDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import com.korit.springboot_study.repository.StudentStudyRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentStudyService {

    @Autowired
    private StudentStudyRepository studentStudyRepository;


    public SuccessResponseDto<List<Major>> getMajorsAll() throws NotFoundException {

        List<Major> foundMajors = studentStudyRepository
                .findMajorAll()
                .orElseThrow(
                        () -> new NotFoundException("학과 데이터가 존재하지 않습니다")
                );

        return new SuccessResponseDto<> (foundMajors);
    }

    public SuccessResponseDto<List<Instructor>> getInstructorAll() throws NotFoundException {

        return new SuccessResponseDto<> (studentStudyRepository
                .findInstructorAll()
                .orElseThrow(
                        () -> new NotFoundException("교수 데이터가 존재하지 않습니다")
                )
        );
    }

    //예외가 터지면 롤백하기, insert 중에 터지는 예외는 어떻게 될지 모르기에 미리 방지한다.
    @Transactional(rollbackFor = DuplicateKeyException.class)
    public SuccessResponseDto<Major> addMajor(ReqAddMajorDto reqAddMajorDto) throws DuplicateKeyException {
        return new SuccessResponseDto<>(
                studentStudyRepository
                        .saveMajor(new Major(0, reqAddMajorDto.getMajorName()))
                        .orElseThrow()
                        //orElseThrow()대신에 get()도 가능
                        //예외처리는 여기서가 아니라 repository에서 되기에 사용될 일이 없다.
        );
    }

    @Transactional(rollbackFor = DuplicateKeyException.class)
    public  SuccessResponseDto<Instructor> addInstructor(ReqAddInstructorDto reqAddInstructorDto) throws DuplicateKeyException {
        return new SuccessResponseDto<>(
                studentStudyRepository
                        .saveInstructor(new Instructor(0, reqAddInstructorDto.getInstructorName()))
                        .orElseThrow()
        );
    }
}
