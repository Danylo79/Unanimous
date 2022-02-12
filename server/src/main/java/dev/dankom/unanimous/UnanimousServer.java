package dev.dankom.unanimous;

import dev.dankom.interfaces.impl.ThreadMethodRunner;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.operation.operations.ShutdownOperation;
import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.group.profile.UIdentity;
import dev.dankom.unanimous.group.profile.UProfile;
import dev.dankom.unanimous.manager.ClassManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@SpringBootApplication
public class UnanimousServer {
    public static ILogger logger = LogManager.addLogger("unanimous", new DefaultLogger());
    private static UnanimousServer instance;

    private FileManager fileManager;
    private ClassManager classManager;

    public UnanimousServer() {
        this.fileManager = new FileManager();
        this.classManager = new ClassManager(fileManager);
        instance = this;

        new ShutdownOperation(new ThreadMethodRunner(() -> classManager.save()), "shutdown-save", logger);
    }

    public void run(String[] args) {
//        SpringApplication.run(UnanimousServer.class, args);

        try {
            classManager.load();
            System.out.println(classManager.getProfileInGlobal(UUID.fromString("28eee7e0-14a1-464d-9691-bf69de12e756")).getBalance());
            System.out.println(classManager.getProfileInGlobal(UUID.fromString("db8ab989-a90f-44e5-af5c-a229cfffc7be")).getBalance());
            classManager.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UnanimousServer().run(args);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    public static UnanimousServer getInstance() {
        return instance;
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("GET", "POST").allowedOrigins("http://local.dankom.ca:4200", "http://turtle.dankom.ca:8081").allowCredentials(true);
        }
    }
}
