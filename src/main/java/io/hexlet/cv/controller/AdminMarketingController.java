package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.marketing.ArticleCreateDTO;
import io.hexlet.cv.dto.marketing.ArticleUpdateDTO;
import io.hexlet.cv.dto.marketing.StoryCreateDTO;
import io.hexlet.cv.dto.marketing.StoryUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.service.ArticleService;
import io.hexlet.cv.service.StoryService;
import jakarta.validation.Valid;

import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/{locale}/admin/marketing")
@AllArgsConstructor
public class AdminMarketingController {

    private final Inertia inertia;
    private final ArticleService articleService;
    private final StoryService storyService;
    // private final ReviewService reviewService;
    // private final TeamService teamService;
    // private final PricingService pricingService;

    // GET /ru/admin/marketing?section=articles - список статей
    // GET /ru/admin/marketing?section=stories - список историй
    // GET /ru/admin/marketing?section=reviews - список отзывов

    // GET /ru/admin/marketing/articles - список статей (для тестов)
    @GetMapping("/articles")
    public ResponseEntity<String> articlesIndex(@PathVariable String locale) {
        var articles = articleService.getAllArticles();
        return inertia.render("Marketing/Articles/Index",
                Map.of(
                        "locale", locale,
                        "activeSection", "articles",
                        "articles", articles
                ));
    }

    // GET /ru/admin/marketing/stories - список историй (для тестов)
    @GetMapping("/stories")
    public ResponseEntity<String> storiesIndex(@PathVariable String locale) {
        var stories = storyService.getAllStories();
        return inertia.render("Marketing/Stories/Index",
                Map.of(
                        "locale", locale,
                        "activeSection", "stories",
                        "stories", stories
                ));
    }

    @GetMapping
    public ResponseEntity<String> index(@PathVariable String locale,
                                        @RequestParam String section) {
        return switch (section) {
            case "articles" -> {
                var articles = articleService.getAllArticles();
                yield inertia.render("Marketing/Articles/Index",
                        Map.of(
                                "locale", locale,
                                "activeSection", "articles",
                                "articles", articles
                        ));
            }
            case "stories" -> {
                var stories = storyService.getAllStories();
                yield inertia.render("Marketing/Stories/Index",
                        Map.of(
                                "locale", locale,
                                "activeSection", "stories",
                                "stories", stories
                        ));
            }
            case "reviews" -> {
                // var reviews = reviewService.getAllReviews();
                yield inertia.render("Marketing/Reviews/Index",
                        Map.of(
                                "locale", locale,
                                "activeSection", "reviews"
                                // "reviews", reviews
                        ));
            }
            case "team" -> {
                // var team = teamService.getAllTeamMembers();
                yield inertia.render("Marketing/Team/Index",
                        Map.of(
                                "locale", locale,
                                "activeSection", "team"
                                // "team", team
                        ));
            }
            case "pricing" -> {
                // var pricing = pricingService.getPricing();
                yield inertia.render("Marketing/Pricing/Index",
                        Map.of(
                                "locale", locale,
                                "activeSection", "pricing"
                                // "pricing", pricing
                        ));
            }
            default -> throw new ResourceNotFoundException("Section not found");
        };
    }

    // GET /ru/admin/marketing/articles/create - форма создания статьи
    @GetMapping("/articles/create")
    public ResponseEntity<String> createArticleForm(@PathVariable String locale) {
        return inertia.render("Marketing/Articles/Create",
                Map.of(
                        "locale", locale,
                        "activeSection", "articles"
                ));
    }

    // GET /ru/admin/marketing/stories/create - форма создания истории
    @GetMapping("/stories/create")
    public ResponseEntity<String> createStoryForm(@PathVariable String locale) {
        return inertia.render("Marketing/Stories/Create",
                Map.of(
                        "locale", locale,
                        "activeSection", "stories"
                ));
    }

    // GET /ru/admin/marketing/articles/{id}/edit - форма редактирования статьи
    @GetMapping("/articles/{id}/edit")
    public ResponseEntity<String> editArticleForm(@PathVariable String locale,
                                                  @PathVariable Long id) {
        var article = articleService.getArticleById(id);
        return inertia.render("Marketing/Articles/Edit",
                Map.of(
                        "locale", locale,
                        "activeSection", "articles",
                        "article", article
                ));
    }

    // GET /ru/admin/marketing/stories/{id}/edit - форма редактирования истории
    @GetMapping("/stories/{id}/edit")
    public ResponseEntity<String> editStoryForm(@PathVariable String locale,
                                                @PathVariable Long id) {
        var story = storyService.getStoryById(id);
        return inertia.render("Marketing/Stories/Edit",
                Map.of(
                        "locale", locale,
                        "activeSection", "stories",
                        "story", story
                ));
    }

    // POST /ru/admin/marketing/articles - создание статьи
    @PostMapping("/articles")
    public ResponseEntity<String> createArticle(@PathVariable String locale,
                                                @Valid @RequestBody ArticleCreateDTO createDTO) {
        articleService.createArticle(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing?section=articles");
    }

    // POST /ru/admin/marketing/stories - создание истории
    @PostMapping("/stories")
    public ResponseEntity<String> createStory(@PathVariable String locale,
                                              @Valid @RequestBody StoryCreateDTO createDTO) {
        storyService.createStory(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing?section=stories");
    }

    // PUT /ru/admin/marketing/articles/{id} - обновление статьи
    @PutMapping("/articles/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable String locale,
                                                @PathVariable Long id,
                                                @Valid @RequestBody ArticleUpdateDTO updateDTO) {
        articleService.updateArticle(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/articles/" + id + "/edit");
    }

    // PUT /ru/admin/marketing/stories/{id} - обновление истории
    @PutMapping("/stories/{id}")
    public ResponseEntity<String> updateStory(@PathVariable String locale,
                                              @PathVariable Long id,
                                              @Valid @RequestBody StoryUpdateDTO updateDTO) {
        storyService.updateStory(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/stories/" + id + "/edit");
    }

    // DELETE /ru/admin/marketing/articles/{id} - удаление статьи
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable String locale,
                                                @PathVariable Long id) {
        articleService.deleteArticle(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=articles");
    }

    // DELETE /ru/admin/marketing/stories/{id} - удаление истории
    @DeleteMapping("/stories/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable String locale,
                                              @PathVariable Long id) {
        storyService.deleteStory(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=stories");
    }

    // POST /ru/admin/marketing/articles/{id}/toggle-publish - переключение публикации статьи
    @PostMapping("/articles/{id}/toggle-publish")
    public ResponseEntity<String> togglePublishArticle(@PathVariable String locale,
                                                       @PathVariable Long id) {
        articleService.togglePublish(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=articles");
    }

    // POST /ru/admin/marketing/stories/{id}/toggle-publish - переключение публикации истории
    @PostMapping("/stories/{id}/toggle-publish")
    public ResponseEntity<String> togglePublishStory(@PathVariable String locale,
                                                     @PathVariable Long id) {
        storyService.togglePublish(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=stories");
    }

    // GET /ru/admin/marketing/home-components - управление компонентами главной страницы
    @GetMapping("/home-components")
    public ResponseEntity<String> homeComponents(@PathVariable String locale) {
        var articles = articleService.getHomepageArticles();
        var stories = storyService.getHomeStories();

        return inertia.render("Marketing/HomeComponents/Index",
                Map.of(
                        "locale", locale,
                        "activeSection", "home-components",
                        "articles", articles,
                        "stories", stories
                ));
    }
}
