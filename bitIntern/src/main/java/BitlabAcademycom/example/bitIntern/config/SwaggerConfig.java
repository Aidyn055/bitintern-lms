package BitlabAcademycom.example.bitIntern.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lms / BitIntern API")
                        .version("1.0.0")
                        .description("Документация всех ENDPOINT-ов системы (LMS).\n" +
                                "Позволяет управлять учебными курсами, главами и уроками.")
                        .contact(new Contact()
                                .name("Bitlab Academy Intern")
                                .email("intern@bitlab.kz")));
    }
}