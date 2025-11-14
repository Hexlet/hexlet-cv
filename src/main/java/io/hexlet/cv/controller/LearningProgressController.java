package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.learning.UserLessonProgressDTO;
import io.hexlet.cv.dto.learning.UserProgramProgressDTO;
import io.hexlet.cv.service.UserLessonProgressService;
import io.hexlet.cv.service.UserProgramProgressService;
import io.hexlet.cv.util.UserUtils;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@AllArgsConstructor
@RequestMapping("/account/my-progress")
@PreAuthorize("isAuthenticated()")
public class LearningProgressController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLessonProgressService.class);

    private final Inertia inertia;
    private final UserProgramProgressService userProgramProgressService;
    private final UserLessonProgressService userLessonProgressService;
    private final UserUtils userUtils;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Object getProgress() {
        LOGGER.debug("Get progress request");
        var user = userUtils.getCurrentUser();

        List<UserProgramProgressDTO> progress = userProgramProgressService.getUserProgress(user.getId());

        Map<String, Object> props = Map.of(
                "progress", progress,
                "pageTitle", "Мое обучение",
                "activeMainSection", "account",
                "activeSubSection", "my-progress"
        );

        LOGGER.debug("[CONTROLLER] Рендеринг страницы Learning/MyProgress/Index с {} свойствами",
                props.size());
        return inertia.render("Learning/MyProgress/Index", props);
    }

    @GetMapping("/program/{programProgressId}/lessons")
    @ResponseStatus(HttpStatus.OK)
    public Object getLessonProgress(@PathVariable Long programProgressId) {
        var user = userUtils.getCurrentUser();
        LOGGER.debug("[CONTROLLER] Получение уроков для programProgressId: {}, пользователь: {}",
                programProgressId, user.getEmail());

        List<UserLessonProgressDTO> lessonsProgress = userLessonProgressService
                .getLessonProgress(user.getId(), programProgressId);
        Map<String, Object> props = Map.of(
                "lessonsProgress", lessonsProgress,
                "programProgressId", programProgressId,
                "pageTitle", "Уроки программы",
                "activeMainSection", "account",
                "activeSubSection", "my-progress"
        );

        LOGGER.debug("[CONTROLLER] Рендеринг страницы Lessons с {} свойствами", props.size());
        return inertia.render("Learning/MyProgress/Lessons", props);
    }

    @PostMapping("/program/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Object startProgram(@RequestParam Long programId) {
        var user = userUtils.getCurrentUser();
        LOGGER.debug("[CONTROLLER] Старт программы {} пользователем {}", programId, user.getEmail());

        userProgramProgressService.startProgram(user.getId(), programId);
        return inertia.redirect("/account/my-progress");
    }

    @PostMapping("/lesson/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Object startLesson(@RequestParam Long programProgressId,
                              @RequestParam Long lessonId) {

        var user = userUtils.getCurrentUser();
        LOGGER.debug("[CONTROLLER] Старт урока {} в программе {} пользователем {}",
                lessonId, programProgressId, user.getEmail());

        userLessonProgressService.startLesson(user.getId(), programProgressId, lessonId);
        return inertia.redirect("/account/my-progress/program/" + programProgressId + "/lessons");

    }

    @PostMapping("/lesson/{lessonProgressId}/complete")
    @ResponseStatus(HttpStatus.OK)
    public Object completeLesson(@PathVariable Long lessonProgressId,
                                 @RequestParam Long programProgressId) {
        userLessonProgressService.completeLesson(lessonProgressId);
        return inertia.redirect("/account/my-progress/program/" + programProgressId + "/lessons");
    }

    @PostMapping("/program/{programProgressId}/complete")
    @ResponseStatus(HttpStatus.OK)
    public Object completeProgram(@PathVariable Long programProgressId) {
        userProgramProgressService.completeProgram(programProgressId);
        return inertia.redirect("/account/my-progress");
    }

    @GetMapping("/")
    public Object defaultSection() {
        LOGGER.debug("[CONTROLLER] GET /account/my-progress/ - Редирект на основную страницу");
        return inertia.redirect("/account/my-progress");
    }
}
