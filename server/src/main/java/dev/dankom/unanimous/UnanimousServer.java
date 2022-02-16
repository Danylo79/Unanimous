package dev.dankom.unanimous;

import dev.dankom.interfaces.impl.ThreadMethodRunner;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.operation.operations.ShutdownOperation;
import dev.dankom.unanimous.auth.UAuthenticationProvider;
import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.manager.ClassManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UnanimousServer {
    public static ILogger logger = LogManager.addLogger("unanimous", new DefaultLogger());
    private static UnanimousServer instance;

    private FileManager fileManager;
    private ClassManager classManager;

    public UnanimousServer() {
        this.fileManager = new FileManager();
        this.classManager = new ClassManager(fileManager, (failedTransaction, throwable) -> throwable.printStackTrace());
        instance = this;

        new ShutdownOperation(new ThreadMethodRunner(() -> classManager.save()), "shutdown-save", logger);
    }

    public void run(String[] args) {
        SpringApplication.run(UnanimousServer.class, args);

//        classManager.addClass("710");
//        classManager.addClass("712");
//        UProfile student1 = classManager.createStudent("710", "Dankom", "1234");
//        UProfile student2 = classManager.createStudent("712", "John", "4321");

//        classManager.transact(student1, student2, 100, "Thread Safe Test");
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
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST")
                    .allowedOrigins("http://local.dankom.ca:4200", "http://turtle.dankom.ca:8081")
                    .allowCredentials(true);
        }
    }

    @Configuration
    @ComponentScan("com.baeldung.security")
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private UAuthenticationProvider authProvider = new UAuthenticationProvider();

        @Override
        public void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(authProvider);
        }
    }
}
