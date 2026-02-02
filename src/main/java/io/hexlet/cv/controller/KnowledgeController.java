package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.KnowledgeService;
import io.hexlet.cv.util.ControllerUtils;
import java.util.HashMap;
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
    private final ControllerUtils utils;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object getKnowledgeHome() {
        log.debug("[KNOWLEDGE CONTROLLER] Get knowledge home request");

        var recentArticles = knowledgeService.getRecentArticles(RECENT_KNOWLEDGE_ITEMS_LIMIT);
        var recentInterviews = knowledgeService.getRecentInterviews(RECENT_KNOWLEDGE_ITEMS_LIMIT);

        var props = new HashMap<>(utils.createAccountProps("knowledge"));
        props.put("recentArticles", recentArticles);
        props.put("recentInterviews", recentInterviews);

        return inertia.render("Account/Knowledge/Index", props);
    }

    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    public Object getArticles(@RequestParam(required = false) String category,
                              @PageableDefault Pageable pageable) {
        log.debug("[KNOWLEDGE CONTROLLER] Get all articles, category: {}, page: {}",
                category, pageable.getPageNumber());

        var articlesPage = knowledgeService.getArticles(category, pageable);

        var props = new HashMap<>(utils.createAccountProps("knowledge-articles"));
        props.put("articles", articlesPage.getContent());
        utils.addPropertyIfPresent(props, "selectedCategory", category);
        props.put("pagination", utils.createPaginationMap(articlesPage, pageable));

        return inertia.render("Account/Knowledge/Articles/Index", props);
    }

    @GetMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getArticleById(@PathVariable Long id) {
        log.debug("[KNOWLEDGE CONTROLLER] Get article by id: {}", id);

        var article = knowledgeService.getArticleById(id);

        var props = new HashMap<>(utils.createAccountProps("knowledge-articles"));
        props.put("article", article);

        return inertia.render("Account/Knowledge/Articles/Show", props);
    }

    @GetMapping("/interviews")
    @ResponseStatus(HttpStatus.OK)
    public Object getAllInterviews(@RequestParam(required = false) String category,
                                   @PageableDefault Pageable pageable) {
        log.debug("[KNOWLEDGE CONTROLLER] Get all interviews, category: {}, page: {}",
                category, pageable.getPageNumber());

        var interviewsPage = knowledgeService.getInterviews(category, pageable);

        Map<String, Object> props = new HashMap<>(utils.createAccountProps("knowledge-interviews"));
        props.put("interviews", interviewsPage.getContent());
        utils.addPropertyIfPresent(props, "selectedCategory", category);
        props.put("pagination", utils.createPaginationMap(interviewsPage, pageable));

        return inertia.render("Account/Knowledge/Interviews/Index", props);
    }

    @GetMapping("/interviews/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getInterviewById(@PathVariable Long id) {
        log.debug("[KNOWLEDGE CONTROLLER] Get interview by id: {}", id);

        var interview = knowledgeService.getInterviewById(id);

        var props = new HashMap<>(utils.createAccountProps("knowledge-interviews"));
        props.put("interview", interview);

        return inertia.render("Account/Knowledge/Interviews/Show", props);
    }

    @GetMapping("/")
    public Object defaultRedirect() {
        log.debug("[KNOWLEDGE CONTROLLER] Redirect from /account/knowledge/ to /account/knowledge");
        return inertia.redirect("/account/knowledge");
    }
}
