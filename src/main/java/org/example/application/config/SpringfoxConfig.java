package org.example.application.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringfoxConfig {

    public static final String TAG_1 = "Employees controller";
    public static final String TAG_2 = "Positions controller";
    public static final String TAG_3 = "Projects controller";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(TAG_1, "Endpoint for making operations with employees data"))
                .tags(new Tag(TAG_2, "Endpoint for making operations with positions data"))
                .tags(new Tag(TAG_3, "Endpoint for making operations with projects data"));
    }
}
