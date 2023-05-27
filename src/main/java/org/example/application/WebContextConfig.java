package org.example.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = "org.example.application")
@Configuration
public class WebContextConfig implements WebMvcConfigurer {
}
