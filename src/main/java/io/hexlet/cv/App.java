package io.hexlet.cv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.datafaker.Faker;

@SpringBootApplication
@EnableJpaAuditing
@RequestMapping("/")
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  public Faker getFaker() {
    return new Faker();
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public String index() {
    return "Hello, World";
  }
}
