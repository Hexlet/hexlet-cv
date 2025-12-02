package io.hexlet.cv.controller.builders;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.StatePurchaseSubscriptionType;
import io.hexlet.cv.model.webinars.Webinar;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseAndSubscriptionTestBuilder {
    private PurchaseAndSubscription subscription = new PurchaseAndSubscription();

    public static PurchaseAndSubscriptionTestBuilder aSubscription() {
        return new PurchaseAndSubscriptionTestBuilder()
                .withDefaultValues();
    }

    private PurchaseAndSubscriptionTestBuilder withDefaultValues() {
        subscription.setOrderNum("TEST-ORDER-" + System.currentTimeMillis());
        subscription.setItemName("Test Item");
        subscription.setPurchasedAt(LocalDate.now());
        subscription.setAmount(BigDecimal.valueOf(100));
        subscription.setState(StatePurchaseSubscriptionType.ACTIVE);
        subscription.setProductType(ProductType.WEBINAR);
        return this;
    }

    public PurchaseAndSubscriptionTestBuilder withUser(User user) {
        subscription.setUser(user);
        return this;
    }

    public PurchaseAndSubscriptionTestBuilder withWebinar(Webinar webinar) {
        subscription.setReferenceId(webinar.getId());
        subscription.setState(StatePurchaseSubscriptionType.AVAILABLE);
        return this;
    }

    public PurchaseAndSubscriptionTestBuilder withState(StatePurchaseSubscriptionType state) {
        subscription.setState(state);
        return this;
    }

    public PurchaseAndSubscription build() {
        return subscription;
    }
}
