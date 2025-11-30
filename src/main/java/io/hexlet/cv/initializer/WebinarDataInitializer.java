package io.hexlet.cv.initializer;

import io.hexlet.cv.model.webinars.Webinar;
import io.hexlet.cv.repository.WebinarRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@AllArgsConstructor
@DependsOn("userDataInitializer")
public class WebinarDataInitializer {

    private WebinarRepository webinarRepo;

    @PostConstruct
    public void initData() {

        var webinar = new Webinar();
        webinar.setWebinarName("Тестовый вебинар");
        webinar.setWebinarDate(LocalDateTime.now());
        webinar.setWebinarRegLink("https://testlink.ru/webinar-reg-link");
        webinar.setWebinarRecordLink("https://testlink.ru/webinar-eecord-link");
        webinar.setPublicated(true);
        webinar.setFeature(false);
        webinarRepo.save(webinar);
    }
}
