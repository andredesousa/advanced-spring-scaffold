package app.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
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

@DisplayName("Swagger")
@ExtendWith(MockitoExtension.class)
public class SwaggerTests {

    @InjectMocks
    transient Swagger swagger;

    @Test
    @DisplayName("#api returns swagger configuration object")
    void api() {
        AuthorizationScope[] authorizationScopes = { new AuthorizationScope("global", "accessEverything") };
        List<SecurityReference> defaultAuth = Arrays.asList(new SecurityReference("JWT", authorizationScopes));
        SecurityContext context = SecurityContext.builder().securityReferences(defaultAuth).build();
        ApiInfo info = new ApiInfoBuilder()
            .title("Spring Boot REST API")
            .description("Spring Boot REST API")
            .version("1.0.0")
            .build();

        Docket config = new Docket(DocumentationType.SWAGGER_2)
            .securityContexts(Arrays.asList(context))
            .securitySchemes(Arrays.asList(new ApiKey("JWT", "Authorization", "header")))
            .apiInfo(info)
            .select()
            .apis(RequestHandlerSelectors.basePackage("app"))
            .paths(PathSelectors.any())
            .build();

        assertThat(swagger.api()).usingRecursiveComparison().isEqualTo(config);
    }
}
