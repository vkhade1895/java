package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The starting point of a small Spring Boot web app. Run it, then open
 * http://localhost:8080/hello in a browser.
 *
 * <p>Keywords: Spring Boot, @SpringBootApplication, SpringApplication.run, auto-configuration,
 * component scan, embedded Tomcat.
 *
 * <p>First written: 2026-11-25.
 */
@SpringBootApplication
public class JavaSamplesApplication {

    // @SpringBootApplication = @SpringBootConfiguration + @EnableAutoConfiguration
    // + @ComponentScan. The scan looks in this package (springboot) and the packages below it,
    // so it finds HelloController, but not the other study packages.

    public static void main(String[] args) {
        // Creates the beans, runs the auto-configuration and starts the embedded Tomcat server
        // on port 8080. The app keeps running until you stop it.
        SpringApplication.run(JavaSamplesApplication.class, args);
    }
}
