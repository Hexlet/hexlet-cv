package io.hexlet.cv.initializer;

import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.StatePurchSubsType;
import io.hexlet.cv.repository.PurchaseAndSubscriptionRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@AllArgsConstructor
@DependsOn({"userDataInitializer", "webinarDataInitializer"})
public class PurchaseAndSubscriptionDataInitializer {

    private final UserRepository userRepository;
    private final WebinarRepository webinarRepository;
    private final PurchaseAndSubscriptionRepository subsRepo;

    @PostConstruct
    public void initData() {

        var testUser = userRepository.findFirstBy();
        var webinar = webinarRepository.findFirstBy();

        PurchaseAndSubscription purchase = new PurchaseAndSubscription();
        purchase.setUser(testUser);
        purchase.setOrderNum("#A-1001");
        purchase.setItemName("Вебинар: " + webinar.getWebinarName());
        purchase.setPurchasedAt(LocalDate.now());
        purchase.setAmount(BigDecimal.valueOf(12345, 2));
        purchase.setState(StatePurchSubsType.ACTIVE);
        purchase.setBillUrl("https://bills.ru/bill/A-1001");
        purchase.setProductType(ProductType.WEBINAR);
        purchase.setReferenceId(webinar.getId());
        subsRepo.save(purchase);
    }
}
