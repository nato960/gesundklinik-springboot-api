package personal.GesundKlinik.shared.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    private final String securitySchemeName = "bearer-key";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                        )
                )
                .info(new Info()
                        .title("GesundKlinik API")
                        .version("1.0.0")
                        .description("RESTful API of the GesundKlinik application, providing user registration, JWT-based authentication," +
                                " and full CRUD operations for doctors and patients, as well as appointment scheduling.")
                        .contact(new Contact()
                                .name("Backend Team")
                                .email("renatolteixeira@hotmail.com"))
                        .license( new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .addTagsItem(new Tag()
                        .name("Users")
                        .description("User registration for application access"))
                .addTagsItem(new Tag()
                        .name("Auth")
                        .description("User authentication using JWT Bearer Token"))
                .addTagsItem(new Tag()
                        .name("Doctors")
                        .description("Manage doctors data: register, update, deactivate, and view"))
                .addTagsItem(new Tag()
                        .name("Pacients")
                        .description("Manage pacients data: register, update, deactivate, and view"))
                .addTagsItem(new Tag()
                        .name("Appointments")
                        .description("Manage appointments: scheduling, rescheduling, cancellation, and consultation")
                );
    }
}

