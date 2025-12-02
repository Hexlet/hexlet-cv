package io.hexlet.cv.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.controller.builders.PurchaseAndSubscriptionTestBuilder;
import io.hexlet.cv.controller.builders.WebinarTestBuilder;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.enums.StatePurchaseSubscriptionType;
import io.hexlet.cv.repository.CalendarEventRepository;
import io.hexlet.cv.repository.PurchaseAndSubscriptionRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
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
    private PurchaseAndSubscriptionRepository purchaseAndSubscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebinarRepository webinarRepository;

    @Autowired
    private CalendarEventRepository eventRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;


    private User testUser;
    private static final String CANDIDATE_EMAIL = "candidate_user@example.com";
    private String candidateToken;


    @AfterEach
    public void cleanUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
        purchaseAndSubscriptionRepository.deleteAll();
        webinarRepository.deleteAll();

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
//given
        var webinar1 = webinarRepository.save(WebinarTestBuilder.aWebinar()
                .withName("Тестовый вебинар 01 ")
                .build());

        var webinar2 = webinarRepository.save(WebinarTestBuilder.aWebinar()
                .withName("Тестовый вебинар 02")
                .build());

        purchaseAndSubscriptionRepository.save(
                PurchaseAndSubscriptionTestBuilder.aSubscription()
                        .withUser(testUser)
                        .withWebinar(webinar1)
                        .build());

        purchaseAndSubscriptionRepository.save(
                PurchaseAndSubscriptionTestBuilder.aSubscription()
                        .withUser(testUser)
                        .withWebinar(webinar2)
                        .build());

        var mvcResult = mockMvc.perform(get("/account/webinars")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
//when
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(webinar1.getWebinarName());
        assertThat(json).contains(webinar2.getWebinarName());
    }

    @Test
    void testRegistrationToWebinars() throws Exception {
//given
        var webinar = webinarRepository.save(WebinarTestBuilder.aWebinar()
                .withName("Тестовый вебинар 03")
                .build());

        purchaseAndSubscriptionRepository.save(
                PurchaseAndSubscriptionTestBuilder.aSubscription()
                        .withUser(testUser)
                        .withWebinar(webinar)
                        .withState(StatePurchaseSubscriptionType.AVAILABLE)
                        .build());
//when
        mockMvc.perform(post("/account/webinars/{webinarId}/registrations", webinar.getId())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/account/webinars"));

        var subs = purchaseAndSubscriptionRepository.findFirstByUserId(testUser.getId());
        assertThat(subs.get().getState()).isEqualTo(StatePurchaseSubscriptionType.REGISTERED);
    }

    @Test
    void testRegistrationToUnavailableWebinars() throws Exception {
//given
        var webinar = webinarRepository.save(WebinarTestBuilder.aWebinar()
                .withName("Тестовый вебинар 04")
                .build());

        purchaseAndSubscriptionRepository.save(
                PurchaseAndSubscriptionTestBuilder.aSubscription()
                        .withUser(testUser)
                        .withWebinar(webinar)
                        .withState(StatePurchaseSubscriptionType.INACTIVE)
                        .build());
//when
        mockMvc.perform(post("/account/webinars/{webinarId}/registrations", webinar.getId())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errors", hasKey("error")))
                .andExpect(flash().attributeExists("errors"));

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
//given
        var webinar = webinarRepository.save(WebinarTestBuilder.aWebinar()
                .withName("Тестовый вебинар 04")
                .build());

        var emptyEvent = eventRepository.findFirstByUserId(testUser.getId());
        assertThat(emptyEvent).isEmpty();

//when
        mockMvc.perform(post("/account/webinars/{webinarId}/calendar-events", webinar.getId())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/account/webinars"));

        var event = eventRepository.findFirstByUserId(testUser.getId());
        assertThat(event).isPresent();

        assertThat(event.get().getReferenceId()).isEqualTo(webinar.getId());
    }

    @Test
    void testAddUnknownWebinarsToCalendar() throws Exception {
//when
        mockMvc.perform(post("/account/webinars/12345/calendar-events")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errors", hasKey("error")))
                .andExpect(flash().attributeExists("errors"));
    }
}
