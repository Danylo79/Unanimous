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
            classManager.addClass("710");
            classManager.addClass("712");
            UProfile student1 = classManager.addStudent(
                    "710",
                    new UProfile(classManager.getGroup("710"), UUID.randomUUID()),
                    new UIdentity(UUID.randomUUID(), "Dankom", "1234")
            );
            UProfile student2 = classManager.addStudent(
                    "712",
                    new UProfile(classManager.getGroup("712"), UUID.randomUUID()),
                    new UIdentity(UUID.randomUUID(), "John", "4321")
            );
            classManager.transact(student1.getID(), student2.getID(), 10, "Test");
            System.out.println(student2.getBalance());
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
