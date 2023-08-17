package com.flab.infrun.common;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setupIntegrationTest() {
        databaseCleaner.afterPropertiesSet();
        databaseCleaner.execute();
    }
}


