package com.strr.mybatis;

import com.strr.mybatis.mapper.TestDemoMapper;
import com.strr.mybatis.model.TestDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MetaobjectApplicationTests {
    @Autowired
    TestDemoMapper testDemoMapper;

    @Test
    void test() {
        TestDemo testDemo = new TestDemo();
        testDemo.setTestKey("new key");
        testDemo.setValue("new value");
        testDemoMapper.insert(testDemo);
    }
}
