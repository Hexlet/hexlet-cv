package io.hexlet.utils;

import io.hexlet.cv.model.PageSection;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelGenerator {

    private Model<User> userModel;
    private Model<PageSection> pageSectionModel;

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {
        userModel = Instancio.of(User.class)
            .ignore(Select.field(User::getId))
            .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
            .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
            .supply(Select.field(User::getLastName), () -> faker.name().lastName())
            .supply(Select.field(User::getEncryptedPassword), () -> faker.internet().password(3, 64))
            .supply(Select.field(User::getRole), () -> RoleType.CANDIDATE) // Роль по умолчанию
            .toModel();

        pageSectionModel = Instancio.of(PageSection.class)
            .ignore(Select.field(PageSection::getId))

            // Оба технических названия устанавливаются в рамках класса теста
            .ignore(Select.field(PageSection::getPageKey))
            .ignore(Select.field(PageSection::getSectionKey))

            .supply(Select.field(PageSection::getTitle), () -> faker.name().title())
            .supply(Select.field(PageSection::getContent), () -> faker.lorem().characters(0, 200))
            .supply(Select.field(PageSection::isActive), () -> true) // Включённость по умолчанию
            .toModel();
    }
}
