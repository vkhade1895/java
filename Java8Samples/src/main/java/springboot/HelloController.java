package springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A small REST endpoint to see the Spring Boot app working: GET /hello returns a short text.
 *
 * <p>Keywords: @RestController, @GetMapping, REST endpoint, Spring MVC.
 *
 * <p>First written: 2026-11-25.
 */
@RestController
public class HelloController {

    // @RestController = @Controller + @ResponseBody: what the method returns is sent back as
    // the response body (here, plain text). It is not the name of a web page.
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}
