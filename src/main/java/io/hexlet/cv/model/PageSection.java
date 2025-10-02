package io.hexlet.cv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "page_sections")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PageSection {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Техническое название обязательно")
    @Column(unique = true)
    private String sectionKey;

    private String title;
    private String content;

    @NotNull(message = "Необходимо указать, включена ли секция")
    private boolean active;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;
}
