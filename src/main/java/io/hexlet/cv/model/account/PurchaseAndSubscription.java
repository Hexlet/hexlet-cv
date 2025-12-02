package io.hexlet.cv.model.account;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.StatePurchaseSubscriptionType;
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
        name = "Purchase_Subscription",
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
    private String orderNum;

    @NotNull
    private String itemName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchasedAt;

    @Column(precision = 15, scale = 2)
    @NotNull
    private BigDecimal amount;

    @NotNull
    private StatePurchaseSubscriptionType state;

    private String billUrl;

    /**
     * ID связанного ресурса (вебинара, курса и т.д.), зависит от {@link #productType}.
     *
     * <b>Полиморфная связь</b>: значение интерпретируется по типу события:
     * <ul>
     *   <li>{@link ProductType#WEBINAR} → ID вебинара</li>
     *   <li>{@link ProductType#COURSE} → ID курса</li>
     * </ul>
     *
     */
    @NotNull
    private ProductType productType;
    @NotNull
    private Long referenceId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
