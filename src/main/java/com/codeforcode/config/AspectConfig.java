package com.codeforcode.config;

import com.codeforcode.aop.TraceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

    @Bean
    public TraceAspect traceAspect() {
        return new TraceAspect();
    }
}
