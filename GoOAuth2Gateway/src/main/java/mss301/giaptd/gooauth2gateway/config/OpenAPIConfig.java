package mss301.giaptd.gooauth2gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String apiDocPath;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Demo")
                        .description("Demo API documents")
                        .version("1.0")
                        .contact(new Contact().name("API"))
                        .license(new License().name("FU")))
                .addServersItem(new Server()
                        .url(apiDocPath)
                        .description("Local Server"));
    }
}
