package io.hexlet.cv.model;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
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
    @Email(message = "Wrong email") @NotBlank(message = "Email is required") private String email;

    private String encryptedPassword;

    @NotBlank(message = "First name is required") private String firstName;

    @NotBlank(message = "Last name is required") private String lastName;

    // Roles: candidate, recruiter, admin, assistant_ai, career_coach, guest
    private String role;
}
