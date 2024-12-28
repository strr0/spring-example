package com.strr.dubbo;

import com.strr.api.IHelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DubboConsumerApplicationTests {
    @DubboReference
    IHelloService helloService;

    @Test
    void test() {
        String hello = helloService.hello();
        System.out.println(hello);
    }
}
