package com.timeless;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author timeless
 * @date 2023/5/28 12:45
 * @desciption:
 */
@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.timeless.mapper")
public class SeckillServer8501 {
    public static void main(String[] args) {
        SpringApplication.run(SeckillServer8501.class, args);
    }
}
