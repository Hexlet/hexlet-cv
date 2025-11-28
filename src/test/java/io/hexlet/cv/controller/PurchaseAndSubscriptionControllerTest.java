package io.hexlet.cv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.enums.StatePurchSubsType;
import io.hexlet.cv.model.webinars.Webinar;
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
public class PurchaseAndSubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PurchaseAndSubscriptionRepository subsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebinarRepository webinarRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    private User testUser;
    private static final String CANDIDATE_EMAIL = "candidate_user@example.com";
    private String candidateToken;



    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
        subsRepo.deleteAll();
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
    public void testAuthorizedIndex() throws Exception {

        Webinar webinar = new Webinar();
        webinar.setWebinarName("Тестовый вебинар");
        webinar.setWebinarDate(LocalDateTime.now().plusDays(1));
        webinar.setWebinarRegLink("https://testlink.ru/reg");
        webinar.setPublicated(true);
        webinar.setFeature(false);
        webinarRepo.save(webinar);


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
        purchase.setWebinar(webinar);

        subsRepo.save(purchase);


        var mvcResult = mockMvc.perform(get("/account/purchase")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(purchase.getItemName());

    }

    @Test
    public void testUnAuthorizedIndex() throws Exception {

        mockMvc.perform(get("/account/purchase")
                        .header("X-Inertia", "true"))
                .andExpect(status().is(401))
                .andReturn();

    }
}
