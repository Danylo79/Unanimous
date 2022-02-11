package dev.dankom.unanimous;

import dev.dankom.unanimous.file.FileManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UnanimousServer {
    private FileManager fileManager;

    public UnanimousServer() {
        this.fileManager = new FileManager();
    }

    public void run(String[] args) {
        SpringApplication.run(UnanimousServer.class, args);
    }

    public static void main(String[] args) {
        new UnanimousServer().run(args);
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("GET", "POST").allowedOrigins("http://local.dankom.ca:4200", "http://turtle.dankom.ca:8081").allowCredentials(true);
        }
    }
}
