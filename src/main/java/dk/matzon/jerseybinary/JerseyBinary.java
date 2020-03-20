package dk.matzon.jerseybinary;

import dk.matzon.jerseybinary.interfaces.rest.RemoteInterface;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JerseyBinary extends SpringBootServletInitializer {

    @Bean
    public ResourceConfig jerseyConfig() {
        return new ResourceConfig().register(RemoteInterface.class);
    }

    public static void main(String[] args) {
        new SpringApplication(JerseyBinary.class).run(args);
    }
}

