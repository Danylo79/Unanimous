package dev.dankom.unanimous;

import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.file.ProfileManager;
import dev.dankom.unanimous.profile.Profile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication
public class UnanimousServer {
    private FileManager fileManager;
    private ProfileManager profileManager;

    public UnanimousServer() {
        this.fileManager = new FileManager();
        this.profileManager = new ProfileManager(fileManager.profiles);
    }

    public void run(String[] args) {
        SpringApplication.run(UnanimousServer.class, args);

        profileManager.addProfile(new Profile(UUID.randomUUID(), "Dankom", "1234", 710, 14, 0, false, new ArrayList<>()));
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
