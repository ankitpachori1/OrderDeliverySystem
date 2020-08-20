package delivery;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Application {

    public static String applicationPort = "8089";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", applicationPort));
        app.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Bean
    public RestTemplate makeRestTemplate(){
        return new RestTemplate();
    }
}

