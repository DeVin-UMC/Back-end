package UMC.DeVin.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "DeVin API Specification",
                description = "SW 분야 종합 커뮤니티 서비스 DeVin API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi devinApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("DeVin API v1")
                .pathsToMatch(paths)
                .build();
    }

}
