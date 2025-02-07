package com.korit.springboot_study.util.db;

import com.korit.springboot_study.entity.Role;
import com.korit.springboot_study.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AutoCreateDML implements CommandLineRunner {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void run(String... args) throws Exception {
//        insertRole();
    }

    //db와 연결, 이걸로 서버 실행 시 한번만 하면됨. bin도 마찬가지.
    //서버 다시 실행 시 이미 실행 중이므로 적용 안되도록 예외 후 적용하기.
    private void insertRole() {
        List<String> roleNames = List.of("USER", "ADMIN", "MANAGER");
        roleMapper.insertAll(roleNames.stream()
                .map(name -> Role.builder().roleName("ROLE_" + name).build())
                .collect(Collectors.toList())
        );
    }

}
