package com.strr.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.strr.mybatis.handler.MyMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.strr.mybatis.mapper")
public class MybatisPlusConfig {
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }
}
