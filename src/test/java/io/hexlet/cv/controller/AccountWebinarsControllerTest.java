package io.hexlet.cv.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.enums.StatePurchSubsType;
import io.hexlet.cv.model.webinars.Webinar;
import io.hexlet.cv.repository.CalendarEventRepository;
import io.hexlet.cv.repository.PurchaseAndSubscriptionRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class AccountWebinarsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PurchaseAndSubscriptionRepository subsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebinarRepository webinarRepo;

    @Autowired
    private CalendarEventRepository eventRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    private User testUser;
    private static final String CANDIDATE_EMAIL = "candidate_user@example.com";
    private String candidateToken;


    @AfterEach
    public void cleanUp() {
        eventRepo.deleteAll();
        userRepository.deleteAll();
        subsRepo.deleteAll();
        webinarRepo.deleteAll();

    }

    @BeforeEach
    public void setUp() {
        cleanUp();

        testUser = new User();
        testUser.setEmail(CANDIDATE_EMAIL);
        testUser.setEncryptedPassword(passwordEncoder.encode("candidate_password"));
        testUser.setRole(RoleType.CANDIDATE);
        testUser = userRepository.save(testUser);
        candidateToken = jwtUtils.generateAccessToken(CANDIDATE_EMAIL);
    }

    @Test
    void testIndexWebinars() throws Exception {

        var webinar = new Webinar();
        webinar.setWebinarName("Тестовый вебинар");
        webinar.setWebinarDate(LocalDateTime.now().plusHours(1));
        webinar.setWebinarRegLink("https://testlink.ru/reg");
        webinar.setPublicated(true);
        webinar.setFeature(false);
        webinar = webinarRepo.save(webinar);

        var webinar2 = new Webinar();
        webinar2.setWebinarName("Тестовый вебинар2");
        webinar2.setWebinarDate(LocalDateTime.now());
        webinar2.setWebinarRegLink("https://testlink.ru/reg");
        webinar2.setPublicated(true);
        webinar2.setFeature(false);
        webinar2 = webinarRepo.save(webinar2);

        PurchaseAndSubscription purchase = new PurchaseAndSubscription();
        purchase.setUser(testUser);
        purchase.setOrderNum("#A-1001");
        purchase.setItemName("Вебинар: " + webinar.getWebinarName());
        purchase.setPurchasedAt(LocalDate.now());
        purchase.setAmount(BigDecimal.valueOf(123.45));
        purchase.setState(StatePurchSubsType.ACTIVE);
        purchase.setBillUrl("https://bills.ru/bill/A-1001");
        purchase.setReferenceId(webinar.getId());
        purchase.setProductType(ProductType.WEBINAR);
        subsRepo.save(purchase);

        PurchaseAndSubscription purchase2 = new PurchaseAndSubscription();
        purchase2.setUser(testUser);
        purchase2.setOrderNum("#A-1002");
        purchase2.setItemName("Вебинар: " + webinar2.getWebinarName());
        purchase2.setPurchasedAt(LocalDate.now());
        purchase2.setAmount(BigDecimal.valueOf(678.90));
        purchase2.setState(StatePurchSubsType.ACTIVE);
        purchase2.setBillUrl("https://bills.ru/bill/A-1002");
        purchase2.setReferenceId(webinar2.getId());
        purchase2.setProductType(ProductType.WEBINAR);
        subsRepo.save(purchase2);

        var mvcResult = mockMvc.perform(get("/account/webinars")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(webinar.getWebinarName());
        assertThat(json).contains(webinar2.getWebinarName());
    }

    @Test
    void testRegistrationToWebinars() throws Exception {

        var webinar = new Webinar();
        webinar.setWebinarName("Тестовый вебинар");
        webinar.setWebinarDate(LocalDateTime.now().plusHours(1));
        webinar.setWebinarRegLink("https://testlink.ru/reg");
        webinar.setPublicated(true);
        webinar.setFeature(false);
        webinar = webinarRepo.save(webinar);

        var emptySubs = subsRepo.findFirstByUserId(testUser.getId());
        assertThat(emptySubs).isEmpty();

        mockMvc.perform(post("/account/webinars/{webinarId}/registrations", webinar.getId())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/Account/Webinars/Index"));


        var subs = subsRepo.findFirstByUserId(testUser.getId());
        assertThat(subs).isPresent();


        assertThat(subs.get().getReferenceId()).isEqualTo(webinar.getId());
    }

    @Test
    void testRegistrationToUnknownWebinar() throws Exception {
        mockMvc.perform(post("/account/webinars/123345/registrations")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errors", hasKey("error")))
                .andExpect(flash().attributeExists("errors"));
    }

    @Test
    void testAddWebinarsToCalendar() throws Exception {

        var webinar = new Webinar();
        webinar.setWebinarName("Тестовый вебинар");
        webinar.setWebinarDate(LocalDateTime.now().plusHours(1));
        webinar.setWebinarRegLink("https://testlink.ru/reg");
        webinar.setPublicated(true);
        webinar.setFeature(false);
        webinar = webinarRepo.save(webinar);

        var emptyEvent = eventRepo.findFirstByUserId(testUser.getId());
        assertThat(emptyEvent).isEmpty();

        mockMvc.perform(post("/account/webinars/{webinarId}/calendar-events", webinar.getId())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/Account/Webinars/Index"));

        var event = eventRepo.findFirstByUserId(testUser.getId());
        assertThat(event).isPresent();

        assertThat(event.get().getReferenceId()).isEqualTo(webinar.getId());
    }

    @Test
    void testAddUnknownWebinarsToCalendar() throws Exception {
        mockMvc.perform(post("/account/webinars/12345/calendar-events")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errors", hasKey("error")))
                .andExpect(flash().attributeExists("errors"));
    }

}
