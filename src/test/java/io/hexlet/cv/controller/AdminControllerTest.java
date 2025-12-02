/*package io.hexlet.cv.controller;




@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private static final String ADMIN_EMAIL = "test_admin@example.com";
    private static final String CANDIDATE_EMAIL = "candidat_user@example.com";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        var admin = new User();
        admin.setEmail(ADMIN_EMAIL);
        admin.setEncryptedPassword(encoder.encode("my_password"));
        admin.setRole(RoleType.ADMIN);
        userRepository.save(admin);

        var candidate = new User();
        candidate.setEmail(CANDIDATE_EMAIL);
        candidate.setEncryptedPassword(encoder.encode("candidat_password_test"));
        candidate.setRole(RoleType.CANDIDATE);
        userRepository.save(candidate);
    }

    @AfterAll
    void clearBase() {
        userRepository.deleteAll();
    }



    @Test
    void testAdminAccessPanel() throws Exception {
        // создаём access_token для ADMIN
        var token = jwtUtils.generateAccessToken(ADMIN_EMAIL);

        mockMvc.perform(get("/ru/admin")
                        .cookie(new Cookie("access_token", token))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void testCandidateAccessAdminPanel() throws Exception {
        var token = jwtUtils.generateAccessToken(CANDIDATE_EMAIL);

        mockMvc.perform(get("/ru/admin")
                        .cookie(new Cookie("access_token", token))
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }



    @Test
    void testAnonymousAccessAdminPanel() throws Exception {
        mockMvc.perform(get("/ru/admin")
                        .header("X-Inertia", "true"))
                .andExpect(status().is4xxClientError());
    }
}
 */
