package io.hexlet.cv;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
// @RequestMapping("/")
// @RestController
// @EnableJpaRepositories(basePackages = "io.hexlet.cv.repository")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

/*
    @Bean
    public Faker getFaker() {
        return new Faker();
    }


      @GetMapping("/")
      @ResponseStatus(HttpStatus.OK)
      public String index() {
      return "Hello, World";
      }
     */
}
