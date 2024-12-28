package com.strr.dubbo.service;

import com.strr.api.IHelloService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class HelloServiceImpl implements IHelloService {
    @Override
    public String hello() {
        return "Nacos-Dubbo-Producer: Hello World!";
    }
}
