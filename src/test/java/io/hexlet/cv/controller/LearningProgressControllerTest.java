package io.hexlet.cv.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.admin.programs.Lesson;
import io.hexlet.cv.model.admin.programs.Program;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.learning.UserLessonProgress;
import io.hexlet.cv.model.learning.UserProgramProgress;
import io.hexlet.cv.repository.LessonRepository;
import io.hexlet.cv.repository.ProgramRepository;
import io.hexlet.cv.repository.UserLessonProgressRepository;
import io.hexlet.cv.repository.UserProgramProgressRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Optional;
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
public class LearningProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserProgramProgressRepository userProgramProgressRepository;

    @Autowired
    private UserLessonProgressRepository userLessonProgressRepository;


    private static final String CANDIDATE_EMAIL = "candidate_user@example.com";
    private String candidateToken;
    private User testUser;
    private Program testProgram;
    private Lesson testLesson;
    private UserProgramProgress testProgramProgress;


    @AfterEach
    void cleanUp() {
        userLessonProgressRepository.deleteAll();
        userProgramProgressRepository.deleteAll();
        lessonRepository.deleteAll();
        programRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        cleanUp();

        testUser = new User();
        testUser.setEmail(CANDIDATE_EMAIL);
        testUser.setEncryptedPassword(passwordEncoder.encode("candidate_password"));
        testUser.setRole(RoleType.CANDIDATE);
        testUser = userRepository.save(testUser);

        candidateToken = jwtUtils.generateAccessToken(CANDIDATE_EMAIL);

        testProgram = new Program();
        testProgram.setTitle("Test Program");
        testProgram.setDescription("Test Program Description");
        testProgram = programRepository.save(testProgram);

        testLesson = new Lesson();
        testLesson.setTitle("Test Lesson");
        testLesson.setContent("Test Lesson Content");
        testLesson.setProgram(testProgram);
        testLesson.setOrderNumber(1);
        testLesson = lessonRepository.save(testLesson);

        testProgramProgress = new UserProgramProgress();
        testProgramProgress.setUser(testUser);
        testProgramProgress.setProgram(testProgram);
        testProgramProgress.setStartedAt(LocalDateTime.now());
        testProgramProgress = userProgramProgressRepository.save(testProgramProgress);
    }

    @Test
    void testCandidateAccessMyProgress() throws Exception {
        mockMvc.perform(get("/account/my-progress")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void testStartProgram() throws Exception {
        mockMvc.perform(post("/account/my-progress/program/start")
                        .param("programId", testProgram.getId().toString())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection());

        Optional<UserProgramProgress> progress = userProgramProgressRepository
                .findByUserIdAndProgramId(testUser.getId(), testProgram.getId());
        assertThat(progress).isPresent();
        assertThat(progress.get().getStartedAt()).isNotNull();
    }

    @Test
    void testStartLesson() throws Exception {
        mockMvc.perform(post("/account/my-progress/lesson/start")
                        .param("programProgressId", testProgramProgress.getId().toString())
                        .param("lessonId", testLesson.getId().toString())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection());

        Optional<UserLessonProgress> lessonProgress = userLessonProgressRepository
                .findByUserIdAndLessonId(testUser.getId(), testLesson.getId());
        assertThat(lessonProgress).isPresent();
        assertThat(lessonProgress.get().getStartedAt()).isNotNull();
        assertThat(lessonProgress.get().getProgramProgress().getId()).isEqualTo(testProgramProgress.getId());
    }


    @Test
    void testCompleteLesson() throws Exception {
        UserLessonProgress lessonProgress = new UserLessonProgress();
        lessonProgress.setUser(testUser);
        lessonProgress.setLesson(testLesson);
        lessonProgress.setProgramProgress(testProgramProgress);
        lessonProgress.setStartedAt(LocalDateTime.now());
        userLessonProgressRepository.save(lessonProgress);

        mockMvc.perform(post("/account/my-progress/lesson/" + testLesson.getId() + "/complete")
                        .param("programProgressId", testProgramProgress.getId().toString())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection());

        Optional<UserLessonProgress> completedLesson = userLessonProgressRepository
                .findByUserIdAndLessonId(testUser.getId(), testLesson.getId());
        assertThat(completedLesson).isPresent();
        assertThat(completedLesson.get().getCompletedAt()).isNotNull();
        assertThat(completedLesson.get().getIsCompleted()).isTrue();
    }


    @Test
    void testCompleteProgram() throws Exception {
        mockMvc.perform(post("/account/my-progress/program/" + testProgram.getId() + "/complete")
                        .param("programProgressId", testProgramProgress.getId().toString())
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection());

        Optional<UserProgramProgress> completedProgram = userProgramProgressRepository
                .findByUserIdAndProgramId(testUser.getId(), testProgram.getId());
        assertThat(completedProgram).isPresent();
        assertThat(completedProgram.get().getCompletedAt()).isNotNull();
        assertThat(completedProgram.get().getIsCompleted()).isTrue();
    }

    @Test
    void testDefaultSectionRedirect() throws Exception {
        var token = jwtUtils.generateAccessToken(CANDIDATE_EMAIL);

        mockMvc.perform(get("/account/my-progress/")
                        .cookie(new Cookie("access_token", token))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testCompletedLessonsCount() throws Exception {
        UserLessonProgress lesson1 = new UserLessonProgress();
        lesson1.setUser(testUser);
        lesson1.setLesson(testLesson);
        lesson1.setProgramProgress(testProgramProgress);
        lesson1.setStartedAt(LocalDateTime.now());
        lesson1.setCompletedAt(LocalDateTime.now());
        lesson1.setIsCompleted(true);
        userLessonProgressRepository.save(lesson1);

        var testLesson2 = new Lesson();
        testLesson2.setTitle("Test Lesson 2");
        testLesson2.setContent("Test Lesson Content 2");
        testLesson2.setProgram(testProgram);
        testLesson2.setOrderNumber(2);
        testLesson2 = lessonRepository.save(testLesson2);

        var lesson2 = new UserLessonProgress();
        lesson2.setUser(testUser);
        lesson2.setLesson(testLesson2);
        lesson2.setProgramProgress(testProgramProgress);
        lesson2.setStartedAt(LocalDateTime.now());
        lesson2.setCompletedAt(LocalDateTime.now());
        lesson2.setIsCompleted(true);
        userLessonProgressRepository.save(lesson2);

        var completedCount = userLessonProgressRepository
                .countCompletedLessonsByProgramProgressId(testProgramProgress.getId());
        assertThat(completedCount).isEqualTo(2L);
    }
}
