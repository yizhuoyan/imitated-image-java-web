package com.yizhuoyan.imitatedimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ImitatedImageJavaWebApplication implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);

    }

    public static void main(String[] args) {
        SpringApplication.run(ImitatedImageJavaWebApplication.class, args);
    }

}
