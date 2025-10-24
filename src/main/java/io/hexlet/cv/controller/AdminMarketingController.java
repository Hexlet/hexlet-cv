package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.marketing.ArticleCreateDTO;
import io.hexlet.cv.dto.marketing.ArticleUpdateDTO;
import io.hexlet.cv.dto.marketing.ReviewCreateDTO;
import io.hexlet.cv.dto.marketing.ReviewUpdateDTO;
import io.hexlet.cv.dto.marketing.StoryCreateDTO;
import io.hexlet.cv.dto.marketing.StoryUpdateDTO;
import io.hexlet.cv.dto.marketing.TeamCreateDTO;
import io.hexlet.cv.dto.marketing.TeamUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.service.ArticleService;
import io.hexlet.cv.service.ReviewService;
import io.hexlet.cv.service.StoryService;
import io.hexlet.cv.service.TeamService;
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
    private final ReviewService reviewService;
    private final TeamService teamService;
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
    // GET /ru/admin/marketing/reviews - список отзывов (для тестов)
    @GetMapping("/reviews")
    public ResponseEntity<String> reviewsIndex(@PathVariable String locale) {
        var reviews = reviewService.getAllReviews();
        return inertia.render("Marketing/Reviews/Index",
                Map.of(
                        "locale", locale,
                        "activeSection", "reviews",
                        "reviews", reviews
                ));
    }

    // GET /ru/admin/marketing/team - список членов команды
    @GetMapping("/team")
    public ResponseEntity<String> teamIndex(@PathVariable String locale) {
        var team = teamService.getAllTeamMembers();
        return inertia.render("Marketing/Team/Index",
                Map.of(
                        "locale", locale,
                        "activeSection", "team",
                        "team", team
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
                var reviews = reviewService.getAllReviews();
                yield inertia.render("Marketing/Reviews/Index",
                        Map.of(
                                "locale", locale,
                                "activeSection", "reviews",
                                "reviews", reviews
                        ));
            }
            case "team" -> {
                var team = teamService.getAllTeamMembers();
                yield inertia.render("Marketing/Team/Index",
                        Map.of(
                                "locale", locale,
                                "activeSection", "team",
                                "team", team
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

    // GET /ru/admin/marketing/reviews/create - форма создания отзыва
    @GetMapping("/reviews/create")
    public ResponseEntity<String> createReviewForm(@PathVariable String locale) {
        return inertia.render("Marketing/Reviews/Create",
                Map.of(
                        "locale", locale,
                        "activeSection", "reviews"
                ));
    }

    // GET /ru/admin/marketing/team/create - форма создания команды
    @GetMapping("/team/create")
    public ResponseEntity<String> createTeamForm(@PathVariable String locale) {
        return inertia.render("Marketing/Team/Create",
                Map.of(
                        "locale", locale,
                        "activeSection", "team"
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

    // GET /ru/admin/marketing/reviews/{id}/edit - форма редактирования отзыва
    @GetMapping("/reviews/{id}/edit")
    public ResponseEntity<String> editReviewForm(@PathVariable String locale,
                                                 @PathVariable Long id) {
        var review = reviewService.getReviewById(id);
        return inertia.render("Marketing/Reviews/Edit",
                Map.of(
                        "locale", locale,
                        "activeSection", "reviews",
                        "review", review
                ));
    }

    // GET /ru/admin/marketing/team/{id}/edit - форма редактирования команды
    @GetMapping("/team/{id}/edit")
    public ResponseEntity<String> editTeamForm(@PathVariable String locale,
                                               @PathVariable Long id) {
        var teamMember = teamService.getTeamMemberById(id);
        return inertia.render("Marketing/Team/Edit",
                Map.of(
                        "locale", locale,
                        "activeSection", "team",
                        "teamMember", teamMember
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

    // POST /ru/admin/marketing/reviews - создание отзыва
    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@PathVariable String locale,
                                               @Valid @RequestBody ReviewCreateDTO createDTO) {
        reviewService.createReview(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing?section=reviews");
    }

    // POST /ru/admin/marketing/team - создание члена команды
    @PostMapping("/team")
    public ResponseEntity<String> createTeamMember(@PathVariable String locale,
                                                   @Valid @RequestBody TeamCreateDTO createDTO) {
        teamService.createTeamMember(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing?section=team");
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

    // PUT /ru/admin/marketing/reviews/{id} - обновление отзыва
    @PutMapping("/reviews/{id}")
    public ResponseEntity<String> updateReview(@PathVariable String locale,
                                               @PathVariable Long id,
                                               @Valid @RequestBody ReviewUpdateDTO updateDTO) {
        reviewService.updateReview(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/reviews/" + id + "/edit");
    }

    // PUT /ru/admin/marketing/team/{id} - обновление члена команды
    @PutMapping("/team/{id}")
    public ResponseEntity<String> updateTeamMember(@PathVariable String locale,
                                                   @PathVariable Long id,
                                                   @Valid @RequestBody TeamUpdateDTO updateDTO) {
        teamService.updateTeamMember(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/team/" + id + "/edit");
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

    // DELETE /ru/admin/marketing/reviews/{id} - удаление отзыва
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable String locale,
                                               @PathVariable Long id) {
        reviewService.deleteReview(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=reviews");
    }

    // DELETE /ru/admin/marketing/team/{id} - удаление члена команды
    @DeleteMapping("/team/{id}")
    public ResponseEntity<String> deleteTeamMember(@PathVariable String locale,
                                                   @PathVariable Long id) {
        teamService.deleteTeamMember(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=team");
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

    // POST /ru/admin/marketing/reviews/{id}/toggle-publish - переключение публикации отзыва
    @PostMapping("/reviews/{id}/toggle-publish")
    public ResponseEntity<String> togglePublishReview(@PathVariable String locale,
                                                      @PathVariable Long id) {
        reviewService.togglePublish(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=reviews");
    }

    // POST /ru/admin/marketing/team/{id}/toggle-publish - переключение публикации команды
    @PostMapping("/team/{id}/toggle-publish")
    public ResponseEntity<String> togglePublishTeam(@PathVariable String locale,
                                                    @PathVariable Long id) {
        teamService.togglePublish(id);
        return inertia.redirect("/" + locale + "/admin/marketing?section=team");
    }

    // GET /ru/admin/marketing/home-components - управление компонентами главной страницы
    @GetMapping("/home-components")
    public ResponseEntity<String> homeComponents(@PathVariable String locale) {
        var articles = articleService.getHomepageArticles();
        var stories = storyService.getHomepageStories();
        var reviews = reviewService.getHomepageReviews();
        var team = teamService.getHomepageTeamMembers();

        return inertia.render("Marketing/HomeComponents/Index",
                Map.of(
                        "locale", locale,
                        "activeSection", "home-components",
                        "articles", articles,
                        "stories", stories,
                        "reviews", reviews,
                        "team", team
                ));
    }

    // POST /ru/admin/marketing/articles/{id}/toggle-homepage - переключение отображения на главной
    @PostMapping("/articles/{id}/toggle-homepage")
    public ResponseEntity<String> toggleArticleHomepage(@PathVariable String locale,
                                                        @PathVariable Long id) {
        articleService.toggleArticleHomepageVisibility(id);
        return inertia.redirect("/" + locale + "/admin/marketing/home-components");
    }

    // POST /ru/admin/marketing/stories/{id}/toggle-homepage - переключение отображения на главной
    @PostMapping("/stories/{id}/toggle-homepage")
    public ResponseEntity<String> toggleStoryHomepage(@PathVariable String locale,
                                                      @PathVariable Long id) {
        storyService.toggleStoryHomepageVisibility(id);
        return inertia.redirect("/" + locale + "/admin/marketing/home-components");
    }

    // POST /ru/admin/marketing/reviews/{id}/toggle-homepage - переключение отображения на главной
    @PostMapping("/reviews/{id}/toggle-homepage")
    public ResponseEntity<String> toggleReviewHomepage(@PathVariable String locale,
                                                       @PathVariable Long id) {
        reviewService.toggleReviewHomepageVisibility(id);
        return inertia.redirect("/" + locale + "/admin/marketing/home-components");
    }

    // POST /ru/admin/marketing/team/{id}/toggle-homepage - переключение отображения на главной
    @PostMapping("/team/{id}/toggle-homepage")
    public ResponseEntity<String> toggleTeamHomepage(@PathVariable String locale,
                                                     @PathVariable Long id) {
        teamService.toggleTeamMemberHomepageVisibility(id);
        return inertia.redirect("/" + locale + "/admin/marketing/home-components");
    }

    // PUT /ru/admin/marketing/articles/{id}/display-order - обновление порядка отображения
    @PutMapping("/articles/{id}/display-order")
    public ResponseEntity<String> updateArticleDisplayOrder(@PathVariable String locale,
                                                            @PathVariable Long id,
                                                            @RequestBody Map<String, Integer> request) {
        Integer displayOrder = request.get("display_order");
        articleService.updateArticleDisplayOrder(id, displayOrder);
        return ResponseEntity.ok().build();
    }

    // PUT /ru/admin/marketing/stories/{id}/display-order - обновление порядка отображения
    @PutMapping("/stories/{id}/display-order")
    public ResponseEntity<String> updateStoryDisplayOrder(@PathVariable String locale,
                                                          @PathVariable Long id,
                                                          @RequestBody Map<String, Integer> request) {
        Integer displayOrder = request.get("display_order");
        storyService.updateStoryDisplayOrder(id, displayOrder);
        return ResponseEntity.ok().build();
    }

    // PUT /ru/admin/marketing/reviews/{id}/display-order - обновление порядка отображения
    @PutMapping("/reviews/{id}/display-order")
    public ResponseEntity<String> updateReviewDisplayOrder(@PathVariable String locale,
                                                           @PathVariable Long id,
                                                           @RequestBody Map<String, Integer> request) {
        Integer displayOrder = request.get("display_order");
        reviewService.updateReviewDisplayOrder(id, displayOrder);
        return ResponseEntity.ok().build();
    }

    // PUT /ru/admin/marketing/team/{id}/display-order - обновление порядка отображения
    @PutMapping("/team/{id}/display-order")
    public ResponseEntity<String> updateTeamDisplayOrder(@PathVariable String locale,
                                                         @PathVariable Long id,
                                                         @RequestBody Map<String, Integer> request) {
        Integer displayOrder = request.get("display_order");
        teamService.updateTeamMemberDisplayOrder(id, displayOrder);
        return ResponseEntity.ok().build();
    }
}
