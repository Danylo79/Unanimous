package dev.dankom.unanimous;

import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.profile.ClassManager;
import dev.dankom.unanimous.profile.UClass;
import dev.dankom.unanimous.profile.UStudent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
public class UnanimousServer {
    private FileManager fileManager;
    private ClassManager classManager;

    public UnanimousServer() {
        this.fileManager = new FileManager();
        this.classManager = new ClassManager(fileManager);
    }

    public void run(String[] args) {
        SpringApplication.run(UnanimousServer.class, args);

        classManager.createClass("7-10");
        classManager.modifyClass("7-10", c -> {
            c.addStudent(new UStudent(UUID.randomUUID(), "Dankom", "1234", 710, 14, 500, false, new ArrayList<>()));
            return c;
        });
        classManager.save();
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
