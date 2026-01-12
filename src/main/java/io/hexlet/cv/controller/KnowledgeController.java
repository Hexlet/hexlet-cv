package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.KnowledgeService;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("account/knowledge")
public class KnowledgeController {
    private static final int RECENT_KNOWLEDGE_ITEMS_LIMIT = 2;

    private final KnowledgeService knowledgeService;
    private final Inertia inertia;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object getKnowledgeHome() {
        log.debug("[KNOWLEDGE CONTROLLER] Get knowledge home request");

        var recentArticles = knowledgeService.getRecentArticles(RECENT_KNOWLEDGE_ITEMS_LIMIT);
        var recentInterviews = knowledgeService.getRecentInterviews(RECENT_KNOWLEDGE_ITEMS_LIMIT);

        Map<String, Object> props = Map.of(
                "recentArticles", recentArticles,
                "recentInterviews", recentInterviews,
                "activeMainSection", "account",
                "activeSubSection", "knowledge"
        );
        return inertia.render("Account/Knowledge/Index", props);
    }

    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    public Object getArticles(@RequestParam(required = false) String category,
                              @PageableDefault Pageable pageable) {
        log.debug("[KNOWLEDGE CONTROLLER] Get all articles, category: {}, page: {}",
                category, pageable.getPageNumber());

        var articlesPage = knowledgeService.getArticles(category, pageable);

        Map<String, Object> props = Map.of(
                "articles", articlesPage.getContent(),
                "selectedCategory", category,
                "activeMainSection", "account",
                "activeSubSection", "knowledge-articles",
                "pagination", Map.of(
                        "currentPage", articlesPage.getNumber(),
                        "totalPages", articlesPage.getTotalPages(),
                        "totalElements", articlesPage.getTotalElements(),
                        "pageSize", articlesPage.getSize()
                )
        );
        return inertia.render("Account/Knowledge/Articles/Index", props);
    }

    @GetMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getArticleById(@PathVariable Long id) {
        log.debug("[KNOWLEDGE CONTROLLER] Get article by id: {}", id);

        var article = knowledgeService.getArticleById(id);

        Map<String, Object> props = Map.of(
                "article", article,
                "activeMainSection", "account",
                "activeSubSection", "knowledge-articles"
        );

        return inertia.render("Account/Knowledge/Articles/Show", props);
    }

    @GetMapping("/interviews")
    @ResponseStatus(HttpStatus.OK)
    public Object getAllInterviews(@RequestParam(required = false) String category,
                                   @PageableDefault Pageable pageable) {
        log.debug("[KNOWLEDGE CONTROLLER] Get all interviews, category: {}, page: {}",
                category, pageable.getPageNumber());

        var interviewsPage = knowledgeService.getInterviews(category, pageable);

        Map<String, Object> props = Map.of(
                "interviews", interviewsPage.getContent(),
                "selectedCategory", category,
                "activeMainSection", "account",
                "activeSubSection", "knowledge-interviews",
                "pagination", Map.of(
                        "currentPage", interviewsPage.getNumber(),
                        "totalPages", interviewsPage.getTotalPages(),
                        "totalElements", interviewsPage.getTotalElements(),
                        "pageSize", interviewsPage.getSize()
                )
        );

        return inertia.render("Account/Knowledge/Interviews/Index", props);
    }

    @GetMapping("/interviews/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getInterviewById(@PathVariable Long id) {
        log.debug("[KNOWLEDGE CONTROLLER] Get interview by id: {}", id);

        var interview = knowledgeService.getInterviewById(id);

        Map<String, Object> props = Map.of(
                "interview", interview,
                "activeMainSection", "account",
                "activeSubSection", "knowledge-interviews"
        );

        return inertia.render("Account/Knowledge/Interviews/Show", props);
    }

    @GetMapping("/")
    public Object defaultRedirect() {
        log.debug("[KNOWLEDGE CONTROLLER] Redirect from /account/knowledge/ to /account/knowledge");
        return inertia.redirect("/account/knowledge");
    }
}
