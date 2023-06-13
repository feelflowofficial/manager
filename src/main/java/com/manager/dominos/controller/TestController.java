package com.manager.dominos.controller;

import com.manager.dominos.dao.databasereadonly.TestDaoRepository;
import com.manager.dominos.domain.entity.Test;
import com.manager.dominos.repository.databasereadonly.TestRepository;
import com.manager.dominos.repository.databasereadonly.query.QuerydslTestRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    private final TestDaoRepository testDaoRepository;

    private final QuerydslTestRepository querydslTestRepository;

    @GetMapping("/test")    // JPA
    public void test () {
        testRepository.findAll();
    }

    @GetMapping("/test2")   // mybatis
    public void test2 () {
        List<Test> testselect = testDaoRepository.testselect();

        for (Test test : testselect) {
            System.out.println("test.getId() = " + test.getId());
        }
    }

    @GetMapping("/testQuerydsl")    // Querydsl
    public void testQuerydsl () {
        querydslTestRepository.testQuery();
    }
}
