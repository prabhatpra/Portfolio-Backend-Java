package com.prabhat.portfolio.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.resend.Resend;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173",
                                "https://prabhat-dis.vercel.app") 
                .allowedMethods("*")
                .allowedHeaders("*");
    }
    
    @Bean
    public Resend resend(@Value("${resend.api.key}") String key) {
    	return new Resend(key);
    }
}