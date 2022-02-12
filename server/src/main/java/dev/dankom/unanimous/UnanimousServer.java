package dev.dankom.unanimous;

import dev.dankom.interfaces.impl.ThreadMethodRunner;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.operation.operations.ShutdownOperation;
import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.manager.ClassManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.*;

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
        SpringApplication.run(UnanimousServer.class, args);
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

//    @Configuration
//    @EnableWebSecurity
//    public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        public void configure(AuthenticationManagerBuilder auth) {
//
//        }
//
//        @Override
//        public void configure(HttpSecurity http) {
//            try {
//                http
//                        .authorizeRequests()
//                        .anyRequest().authenticated()
//                        .and()
//                        .formLogin()
//                        .loginPage("/login")
//                        .permitAll();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @EnableWebMvc
//    @ComponentScan("org.springframework.security.samples.mvc")
//    public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
//        @Override
//        public void addViewControllers(ViewControllerRegistry registry) {
//            registry.addViewController("/login").setViewName("login");
//            registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        }
//    }
}
