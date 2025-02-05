package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.study.ReqAddStudentDto;
import com.korit.springboot_study.dto.request.study.ReqStudentDto;
import com.korit.springboot_study.dto.response.study.RespAddStudentDto;
import com.korit.springboot_study.dto.response.study.RespStudentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Controller
@RestController  //내부의 모든 메소드에 @ResponseBody을 붙여준다. Controller + ResponseBody
@Api(tags = "REST API 수업") //컨트롤러 이름 바꾸기
public class FirstRestController {
    //@ResponseBody
    @GetMapping("/api/hello")
    public Map<String, Object> hello(HttpServletRequest request) {
        String age = request.getParameter("age"); //postman에서 보낸 요청 받기
        String address = request.getParameter("address");

        System.out.println(age);
        System.out.println(address);

        return Map.of("name", "김준일");
    }

    @GetMapping("/api/hello2")
    public Map<String, Object> hello2(
            @RequestParam int age, //요청값 받기
            @RequestParam String address
    ) {

        System.out.println(age);
        System.out.println(address);

        return Map.of("name", "김준일");
    }

    //메소드 이름 바꾸기 및 설명 부여
    @ApiOperation(value = "학생 조회(일반 for문)", notes = "일반 for문을 사용하여 선형 탐색 학습")
    @GetMapping("/api/student")
    public Map<String, Object> getStudent(
            @ApiParam(value = "조회할 학생 인덱스", required = false) //매개변수 이름 설정, required는 필수여부
            @RequestParam(name = "studentId") int id //변수명과 매개변수 명이 같으면 생략할 수 있다.
    ) {
        List<Map<String, Object>> students = new ArrayList<>();
        students.add(Map.of("id", 1, "name", "최석현", "age", 26));
        students.add(Map.of("id", 2, "name", "백진우", "age", 32));
        students.add(Map.of("id", 3, "name", "이주원", "age", 28));
        students.add(Map.of("id", 4, "name", "정영훈", "age", 26));

        int foundIndex = -1;

        for(int i = 0; i < students.size(); i++) {
            if((Integer) students.get(i).get("id") == id) {
                foundIndex = i;
                break;
            }
        }
        if(foundIndex == -1) {
            return Map.of("error", "찾지 못했음");
        }
        return students.get(foundIndex);
    }

    @ApiOperation(value = "학생조회(향상된 for문)", notes = "향상된 for문으로 선형 탐색 학습")
    @GetMapping("/api/student2")
    public Map<String, Object> getStudent2(
            @ApiParam(value = "조회할 학생 인덱스", required = true)
            @RequestParam int id
    ) {
        List<Map<String, Object>> students = new ArrayList<>();
        students.add(Map.of("id", 1, "name", "최석현", "age", 26));
        students.add(Map.of("id", 2, "name", "백진우", "age", 32));
        students.add(Map.of("id", 3, "name", "이주원", "age", 28));
        students.add(Map.of("id", 4, "name", "정영훈", "age", 26));

        Map<String, Object> foundStudent = null;

        for (Map<String, Object> student : students) {
            if ((Integer) student.get("id") == id) {
                foundStudent = student;
                break;
            }
        }

        if(foundStudent == null) {
            foundStudent = Map.of("error", "찾지 못했음");
        }
        return foundStudent;

//        for (Map<String, Object> student : students) {
//            if ((int) student.get("id") == id) {
//                return student;
//            }
//        }
//        return Map.of("error", "찾지 못했음");
    }


    @ApiOperation(value = "학생조회(stream)", notes = "stream으로 학생조회")
    @GetMapping("/api/student3")
    public Map<String, Object> getStudent3(
            @ApiParam(value = "조회할 학생인덱스", required = true)
            @RequestParam int studentId
    ) {
        List<Map<String, Object>> students = new ArrayList<>();
        students.add(Map.of("id", 1, "name", "최석현", "age", 26));
        students.add(Map.of("id", 2, "name", "백진우", "age", 32));
        students.add(Map.of("id", 3, "name", "이주원", "age", 28));
        students.add(Map.of("id", 4, "name", "정영훈", "age", 26));

        Map<String, Object> responseData = students.stream()
                .filter(student -> (Integer) student.get("id") == studentId)
                .findFirst()
                .orElse(Map.of("error", "찾지못했음"));

        return responseData;
    }


    @GetMapping("/api/student4/{studentId}") //경로에서 키값을 사용
    public Map<String, Object> getStudent4(
        @ApiParam(value = "학생ID", required = true)
        @PathVariable int studentId, //경로에 있는 값을 사용
        @ModelAttribute
        ReqStudentDto reqStudentDto
    ){
        return Map.of("id", studentId, "name", reqStudentDto.getName(), "age", reqStudentDto.getAge());
    }

    //실제로 서버에 응답하는 데이터
    @GetMapping("/api/student5/{studentId}")
    public RespStudentDto getStudent5(
            @ApiParam(value = "학생ID", required = true)
            @PathVariable int studentId,
            @ModelAttribute
            ReqStudentDto reqStudentDto
    ){
        return new RespStudentDto(100, "김준일", 33);
    }



    //post랑 put 요청은 json으로 전달한다
    //DTO을 쓰지 않을 경우 Map을 사용한다.
    @PostMapping("/api/student")
    @ApiOperation(value = "학생추가", notes = "학생정보를 입력받아 user_tb에 데이터를 저장합니다")
    public ResponseEntity<RespAddStudentDto> addStudent(
            @RequestBody ReqAddStudentDto reqAddStudentDto
    ) {
        System.out.println(reqAddStudentDto);
        return ResponseEntity.badRequest().body(new RespAddStudentDto("학생 추가 실패", false));
        //return ResponseEntity.ok().body(new RespAddStudentDto("학생 추가 완료", true));
    }

    @PutMapping("/api/student/{studentId}")
    @ApiOperation(value= "학생 정보 수정", notes = "학생 ID를 기준으로 학생 정보를 수정합니다")
    public ResponseEntity<?> updateStudent(
            @ApiParam(value = "학생ID", example = "1", required = true)
            @PathVariable int studentId, @RequestBody Map<String, Object> reqBody
    ) {
        System.out.println(reqBody);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "학생 정보 삭제", notes = "학생 ID를 기준으로 정보를 삭제합니다")
    @DeleteMapping("/api/student/{studentId}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable int studentId
    ) {
        return ResponseEntity.ok().body(null);
    }

}
