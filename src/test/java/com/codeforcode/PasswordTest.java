package com.codeforcode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void test1(){
        String password = "qpwoei123!";
        String encode = passwordEncoder.encode(password);
        Assertions.assertTrue(BCrypt.checkpw(password, encode));
    }

    @Test
    public void test2(){

    }
}
