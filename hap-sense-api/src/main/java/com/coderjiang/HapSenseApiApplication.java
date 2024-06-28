package com.coderjiang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.coderjiang.mapper")
public class HapSenseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HapSenseApiApplication.class, args);
    }

}
