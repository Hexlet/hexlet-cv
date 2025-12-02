package io.hexlet.cv.model.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.CalendarEventType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "calendar_event")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    private String title;

    private String description;

    /**
     * ID связанного ресурса (вебинара, встречи и т.д.), зависит от {@link #eventType}.
     *
     * <b>Полиморфная связь</b>: значение интерпретируется по типу события:
     * <ul>
     *   <li>{@link CalendarEventType#WEBINAR} → ID вебинара</li>
     *   <li>{@link CalendarEventType#MEETING} → ID встречи</li>
     * </ul>
     *
     */
    private Long referenceId;
    private CalendarEventType eventType;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime finishAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
