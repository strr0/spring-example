package com.strr.nacos;

import com.strr.nacos.client.HelloClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NacosConsumerApplicationTests {
    @Autowired
    HelloClient helloClient;

    @Test
    void test() {
        String hello = helloClient.hello();
        System.out.println(hello);
    }
}
