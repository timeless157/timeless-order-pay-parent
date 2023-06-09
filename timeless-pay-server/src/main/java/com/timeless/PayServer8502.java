package com.timeless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author timeless
 * @date 2023/5/28 12:45
 * @desciption:
 */
@SpringBootApplication
@EnableFeignClients
public class PayServer8502 {
    public static void main(String[] args) {
        SpringApplication.run(PayServer8502.class, args);
    }
}
