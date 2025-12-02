package io.hexlet.cv.dto.account;

import io.hexlet.cv.model.enums.StatePurchaseSubscriptionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseSubscriptionDto {

    private Long id;
    private Long userId;
    private String orderNum;
    private String itemName;
    private LocalDate purchasedAt;
    private BigDecimal amount;
    private StatePurchaseSubscriptionType state;
    private String billUrl;
}
