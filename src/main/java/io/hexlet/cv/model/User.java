package io.hexlet.cv.model;
import static jakarta.persistence.GenerationType.IDENTITY;

import io.hexlet.cv.converter.RoleTypeConverter;
import io.hexlet.cv.model.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email(message = "Укажите корректный email-адрес")
    @NotBlank(message = "Email обязателен")
    private String email;

    private String encryptedPassword;

    @NotBlank(message = "Имя обязательно")
    private String firstName;

    @NotBlank(message = "Фамилия обязательна")
    private String lastName;

    @Column(nullable = false)
    @Convert(converter = RoleTypeConverter.class)
    private RoleType role;
}
