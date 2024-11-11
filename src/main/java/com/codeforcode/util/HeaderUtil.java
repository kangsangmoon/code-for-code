package com.codeforcode.util;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeaderUtil {
    @Value("${jwt.header}")
    public static String header;

    @Value("${jwt.refresh-header}")
    public static String refreshHeader;

    /**
     * @implSpec 이 메소드에서는 request header에 포함되어있는 JWT 토큰 값을 가져오게 해준다
     */
    public static String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        log.info("Resolve Token -> {}", token);
        return token;
    }

    public static String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Refresh-Token");
        if (bearerToken == null) {
            return null;
        }
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}