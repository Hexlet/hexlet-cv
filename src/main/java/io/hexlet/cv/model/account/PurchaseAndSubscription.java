package io.hexlet.cv.model.account;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.StatePurchSubsType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
        name = "purch_subs",
        indexes = { @Index(name = "idx_purch_user_purchased", columnList = "userId, purchasedAt") }
)
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PurchaseAndSubscription {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String orderNum;  // Номер заказа типа #A-1042

    @NotNull
    private String itemName;  // название товара/подписки и тд

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchasedAt;   // наверное это дата с какого числа подписка или дат когда куплено

    @Column(precision = 15, scale = 2)
    @NotNull
    private BigDecimal amount; // Сумма

    @NotNull
    private StatePurchSubsType state;

    private String billUrl; // ссылка на файл со счетом или на сторонний ресурс банка и тд.


    // Полиморфная связь
    @NotNull
    private ProductType productType;
    @NotNull
    private Long referenceId;  // id вебинара, курса, подписки и тд



   // @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "webinar_id")
   // private Webinar webinar;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
