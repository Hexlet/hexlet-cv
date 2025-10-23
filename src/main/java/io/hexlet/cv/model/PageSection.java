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

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "page_sections")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PageSection {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    // "main", "profile" и др.
    @NotBlank(message = "Техническое название страницы с секцией обязательно")
    @Column(name = "pageKey")
    private String pageKey;

    // "about_us", "team", "pricing" и др.
    @NotBlank(message = "Техническое название секции обязательно")
    @Column(name = "sectionKey", unique = true)
    private String sectionKey;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @NotNull(message = "Статус активности секции обязателен")
    @Column(name = "active")
    private boolean active;

    @CreatedDate
    @Column(name = "createdAt")
    private String createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private String updatedAt;
}
