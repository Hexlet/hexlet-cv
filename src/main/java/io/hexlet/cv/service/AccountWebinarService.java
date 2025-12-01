
/*
package io.hexlet.cv.service;


@Service
@AllArgsConstructor
public class AccountWebinarService {

    private final PurchaseAndSubscriptionRepository subsRepository;

    public Map<String, Object> indexWebinars(Long id, Pageable pageable) {

        Map<String, Object> props = new HashMap<>();


        var webinars = subsRepository.findByUserIdAndProductType(id, ProductType.WEBINAR, pageable)
                .map(PurchaseAndSubscription::getWebinar);


        props.put("currentPage", webinars.getNumber());
        props.put("totalPages", webinars.getTotalPages());
        props.put("totalItems", webinars.getTotalElements());
        props.put("webinars", webinars.getContent());
        return props;

    }
}


 */
