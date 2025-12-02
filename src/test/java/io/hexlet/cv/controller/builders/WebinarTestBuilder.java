package io.hexlet.cv.controller.builders;

import io.hexlet.cv.model.webinars.Webinar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebinarTestBuilder {

    private Webinar webinar = new Webinar();

    public static WebinarTestBuilder aWebinar() {

        return new WebinarTestBuilder().withDefaultValues();
    }

    private WebinarTestBuilder withDefaultValues() {
        webinar.setWebinarDate(LocalDate.now().plusDays(1).atTime(12, 0));
        webinar.setWebinarRegLink("https://test-reg-link.ru");
        webinar.setWebinarRecordLink("https://test-record-link.ru");
        webinar.setPublicated(true);
        webinar.setFeature(false);
        webinar.setWebinarName("Default webinar name");
        return this;
    }

    public WebinarTestBuilder withName(String name) {
        webinar.setWebinarName(name);
        return this;
    }

    public Webinar build() {
        return webinar;
    }

    public WebinarTestBuilder withDate(String date) {
        webinar.setWebinarDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return this;
    }

    public WebinarTestBuilder withRegLink(String regLink) {
        webinar.setWebinarRegLink(regLink);
        return this;
    }

    public WebinarTestBuilder withRecordLink(String recordLink) {
        webinar.setWebinarRecordLink(recordLink);
        return this;
    }
}
