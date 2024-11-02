package com.codeforcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching  //SpringBoot에서 캐싱 기능이 필요하다고 전달
@SpringBootApplication
public class CodeForCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeForCodeApplication.class, args);
    }

}
