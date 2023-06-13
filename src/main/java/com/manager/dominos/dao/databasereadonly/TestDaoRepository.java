package com.manager.dominos.dao.databasereadonly;

import com.manager.dominos.domain.entity.Test;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDaoRepository {

    public List<Test> testselect();

}
