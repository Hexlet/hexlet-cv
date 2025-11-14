package io.hexlet.cv.dto.account;

import io.hexlet.cv.model.enums.StatePurchSubsType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchSubsDTO {

    private Long id;
    private Long userId;
    private String orderNum;
    private String itemName;
    private LocalDate purchasedAt;
    private BigDecimal amount;
    private StatePurchSubsType state;
    private String billUrl;
}
