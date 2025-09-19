package io.hexlet.cv;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class App {
    public static void main(String[] args) {
        getEnvironmentFromEnvFile();
        SpringApplication.run(App.class, args);
    }

    private static void getEnvironmentFromEnvFile() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        Set<DotenvEntry> dotenvInFile = dotenv.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE);
        dotenvInFile.forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue()));
    }
}
