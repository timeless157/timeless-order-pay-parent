package com.timeless;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author timeless
 * @date 2023/5/28 12:45
 * @desciption:
 */
@SpringBootApplication
@MapperScan("com.timeless.mapper")
public class ProductServer8500 {
    public static void main(String[] args) {
        SpringApplication.run(ProductServer8500.class, args);
    }
}
