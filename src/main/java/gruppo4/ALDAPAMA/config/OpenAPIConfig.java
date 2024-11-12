package gruppo4.ALDAPAMA.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("info@aldapama.com");
        contact.setName("ALDAPAMA");
        contact.setUrl("https://www.aldapama.com");

        Info info = new Info()
                .title("ALDAPAMA Energy Service API")
                .version("1.0")
                .contact(contact)
                .description("This API opens up the world of ALDAPAMA Energy Service, the \"finest\" hypertech scam in town! \uD83D\uDEA8 Looking for energy? We’ve got you covered — just be ready for a shock (especially when you see the bill)! ⚡\uD83D\uDCB8\n" +
                        "\n" +
                        "P.S. Our customer service is legendary. Call anytime! We can’t promise clarity… or speed… or even an answer!\n" +
                        "\n");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
