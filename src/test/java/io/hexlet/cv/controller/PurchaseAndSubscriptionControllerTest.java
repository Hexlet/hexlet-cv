package io.hexlet.cv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.account.PurchSubs;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.enums.StatePurchSubsType;
import io.hexlet.cv.repository.PurchSubsRepository;
import io.hexlet.cv.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseAndSubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PurchSubsRepository subsRepo;

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
        subsRepo.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        subsRepo.deleteAll();
    }

    @Test
    public void testIndex() throws Exception {

        var user = new User();
        user.setEmail("test@google.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEncryptedPassword("123456");
        user.setRole(RoleType.CANDIDATE);
        var saved = userRepository.save(user);

        PurchSubs subs = new PurchSubs();
        subs.setUser(saved);
        subs.setOrderNum("#A-1042");
        subs.setItemName("Test subscription");
        subs.setPurchasedAt(LocalDate.parse("2020-10-10", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        subs.setAmount(BigDecimal.valueOf(12345, 2));
        subs.setState(StatePurchSubsType.ACTIVE);
        subs.setBillUrl("https://www.google.com");
        subsRepo.save(subs);

        var mvcResult = mockMvc.perform(get("/account/" + saved.getId() + "/purchase")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(subs.getItemName());

    }
}
