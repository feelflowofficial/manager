package com.manager.dominos.repository.databasereadonly.query;

import com.manager.dominos.domain.entity.QTest;
import com.manager.dominos.domain.entity.Test;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuerydslTestRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    public void testQuery() {
        QTest qTest = QTest.test;

        List<Test> result = jpaQueryFactory
                .selectFrom(qTest)
                .fetch();

        for (Test test : result) {
            System.out.println("test.getId() = " + test.getId());
        }

    }
}
