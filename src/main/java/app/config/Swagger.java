package app.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .securityContexts(Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(new ApiKey("JWT", "Authorization", "header")))
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("app"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Spring Boot REST API")
            .description("Spring Boot REST API")
            .version("1.0.0")
            .build();
    }

    private SecurityContext securityContext() {
        AuthorizationScope[] authorizationScopes = { new AuthorizationScope("global", "accessEverything") };
        List<SecurityReference> defaultAuth = Arrays.asList(new SecurityReference("JWT", authorizationScopes));

        return SecurityContext.builder().securityReferences(defaultAuth).build();
    }
}
