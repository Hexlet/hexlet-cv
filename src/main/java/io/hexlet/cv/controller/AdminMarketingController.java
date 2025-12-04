package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.marketing.ArticleCreateDTO;
import io.hexlet.cv.dto.marketing.ArticleUpdateDTO;
import io.hexlet.cv.dto.marketing.PricingCreateDTO;
import io.hexlet.cv.dto.marketing.PricingUpdateDTO;
import io.hexlet.cv.dto.marketing.ReviewCreateDTO;
import io.hexlet.cv.dto.marketing.ReviewUpdateDTO;
import io.hexlet.cv.dto.marketing.StoryCreateDTO;
import io.hexlet.cv.dto.marketing.StoryUpdateDTO;
import io.hexlet.cv.dto.marketing.TeamCreateDTO;
import io.hexlet.cv.dto.marketing.TeamUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.service.ArticleService;
import io.hexlet.cv.service.PricingPlanService;
import io.hexlet.cv.service.ReviewService;
import io.hexlet.cv.service.StoryService;
import io.hexlet.cv.service.TeamService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@AllArgsConstructor
@RequestMapping("/{locale}/admin/marketing")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMarketingController {

    private final Inertia inertia;
    private final ArticleService articleService;
    private final StoryService storyService;
    private final ReviewService reviewService;
    private final TeamService teamService;
    private final PricingPlanService pricingPlanService;

    @GetMapping("/{section}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> index(@PathVariable String locale,
                                        @PathVariable String section) {

        Map<String, Object> baseProps = Map.of(
                "locale", locale,
                "activeMainSection", "marketing", // Для выделения "Маркетинг" в левом меню
                "activeSubSection", section        // Для выделения подраздела
        );

        return switch (section) {
            case "articles" -> {
                var articles = articleService.getAllArticles();
                Map<String, Object> props = new HashMap<>(baseProps);
                props.putAll(Map.of(
                        "articles", articles,
                        "pageTitle", "Статьи"
                ));
                yield inertia.render("Admin/Marketing/Articles/Index", props);
            }
            case "stories" -> {
                var stories = storyService.getAllStories();
                Map<String, Object> props = new HashMap<>(baseProps);
                props.putAll(Map.of(
                        "stories", stories,
                        "pageTitle", "Истории"
                ));
                yield inertia.render("Admin/Marketing/Stories/Index", props);
            }
            case "reviews" -> {
                var reviews = reviewService.getAllReviews();
                Map<String, Object> props = new HashMap<>(baseProps);
                props.putAll(Map.of(
                        "reviews", reviews,
                        "pageTitle", "Отзывы"
                ));
                yield inertia.render("Admin/Marketing/Reviews/Index", props);
            }
            case "team" -> {
                var team = teamService.getAllTeamMembers();
                Map<String, Object> props = new HashMap<>(baseProps);
                props.putAll(Map.of(
                        "team", team,
                        "pageTitle", "Команда"
                ));
                yield inertia.render("Admin/Marketing/Team/Index", props);
            }
            case "pricing" -> {
                var pricing = pricingPlanService.getAllPricing();
                Map<String, Object> props = new HashMap<>(baseProps);
                props.putAll(Map.of(
                        "pageTitle", "Тарифы и скидки",
                        "pricing", pricing
                ));
                yield inertia.render("Admin/Marketing/Pricing/Index", props);
            }
            case "home-components" -> {
                var articles = articleService.getHomepageArticles();
                var stories = storyService.getHomepageStories();
                var reviews = reviewService.getHomepageReviews();
                var team = teamService.getHomepageTeamMembers();
                Map<String, Object> props = new HashMap<>(baseProps);
                props.putAll(Map.of(
                        "articles", articles,
                        "stories", stories,
                        "reviews", reviews,
                        "team", team,
                        "pageTitle", "Компоненты главной"
                ));
                yield inertia.render("Admin/Marketing/HomeComponents/Index", props);
            }
            default -> throw new ResourceNotFoundException("Section not found");
        };
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> defaultSection(@PathVariable String locale) {
        return inertia.redirect("/" + locale + "/admin/marketing/articles");
    }

    @GetMapping("/{section}/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createForm(@PathVariable String locale,
                                             @PathVariable String section) {
        Map<String, Object> props = Map.of(
                "locale", locale,
                "activeMainSection", "marketing",
                "activeSubSection", section
        );

        return switch (section) {
            case "articles" -> inertia.render("Admin/Marketing/Articles/Create", props);
            case "stories" -> inertia.render("Admin/Marketing/Stories/Create", props);
            case "reviews" -> inertia.render("Admin/Marketing/Reviews/Create", props);
            case "team" -> inertia.render("Admin/Marketing/Team/Create", props);
            case "pricing" -> inertia.render("Admin/Marketing/Pricing/Create", props);
            default -> throw new ResourceNotFoundException("Create form not found for section: " + section);
        };
    }

    @GetMapping("/{section}/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> editForm(@PathVariable String locale,
                                           @PathVariable String section,
                                           @PathVariable Long id) {
        Map<String, Object> baseProps = Map.of(
                "locale", locale,
                "activeMainSection", "marketing",
                "activeSubSection", section
        );

        return switch (section) {
            case "articles" -> {
                var article = articleService.getArticleById(id);
                Map<String, Object> props = new HashMap<>(baseProps);
                props.put("article", article);
                yield inertia.render("Admin/Marketing/Articles/Edit", props);
            }
            case "stories" -> {
                var story = storyService.getStoryById(id);
                Map<String, Object> props = new HashMap<>(baseProps);
                props.put("story", story);
                yield inertia.render("Admin/Marketing/Stories/Edit", props);
            }
            case "reviews" -> {
                var review = reviewService.getReviewById(id);
                Map<String, Object> props = new HashMap<>(baseProps);
                props.put("review", review);
                yield inertia.render("Admin/Marketing/Reviews/Edit", props);
            }
            case "team" -> {
                var teamMember = teamService.getTeamMemberById(id);
                Map<String, Object> props = new HashMap<>(baseProps);
                props.put("teamMember", teamMember);
                yield inertia.render("Admin/Marketing/Team/Edit", props);
            }
            case "pricing" -> {
                var pricing = pricingPlanService.getPricingById(id);
                Map<String, Object> props = new HashMap<>(baseProps);
                props.put("pricing", pricing);
                yield inertia.render("Admin/Marketing/Pricing/Edit", props);
            }
            default -> throw new ResourceNotFoundException("Edit form not found for section: " + section);
        };
    }

    // Создание статьи
    @PostMapping("/articles")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> createArticle(@PathVariable String locale,
                                                @Valid @RequestBody ArticleCreateDTO createDTO) {
        articleService.createArticle(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/articles");
    }

    // Создание истории
    @PostMapping("/stories")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> createStory(@PathVariable String locale,
                                              @Valid @RequestBody StoryCreateDTO createDTO) {
        storyService.createStory(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/stories");
    }

    // Создание отзыва
    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> createReview(@PathVariable String locale,
                                               @Valid @RequestBody ReviewCreateDTO createDTO) {
        reviewService.createReview(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/reviews");
    }

    // Создание члена команды
    @PostMapping("/team")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> createTeamMember(@PathVariable String locale,
                                                   @Valid @RequestBody TeamCreateDTO createDTO) {
        teamService.createTeamMember(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/team");
    }

    @PostMapping("/pricing")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> createPricing(@PathVariable String locale,
                                                @Valid @RequestBody PricingCreateDTO createDTO) {
        pricingPlanService.createPricing(createDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/pricing");
    }

    @PutMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> updateArticle(@PathVariable String locale,
                                                @PathVariable Long id,
                                                @Valid @RequestBody ArticleUpdateDTO updateDTO) {
        articleService.updateArticle(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/articles");
    }

    @PutMapping("/stories/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> updateStory(@PathVariable String locale,
                                              @PathVariable Long id,
                                              @Valid @RequestBody StoryUpdateDTO updateDTO) {
        storyService.updateStory(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/stories");
    }

    @PutMapping("/reviews/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> updateReview(@PathVariable String locale,
                                               @PathVariable Long id,
                                               @Valid @RequestBody ReviewUpdateDTO updateDTO) {
        reviewService.updateReview(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/reviews");
    }

    @PutMapping("/team/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> updateTeamMember(@PathVariable String locale,
                                                   @PathVariable Long id,
                                                   @Valid @RequestBody TeamUpdateDTO updateDTO) {
        teamService.updateTeamMember(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/team");
    }

    @PutMapping("/pricing/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> updatePricing(@PathVariable String locale,
                                                @PathVariable Long id,
                                                @Valid @RequestBody PricingUpdateDTO updateDTO) {
        pricingPlanService.updatePricing(id, updateDTO);
        return inertia.redirect("/" + locale + "/admin/marketing/pricing");
    }

    @DeleteMapping("/{section}/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> delete(@PathVariable String locale,
                                         @PathVariable String section,
                                         @PathVariable Long id) {
        switch (section) {
            case "articles" -> articleService.deleteArticle(id);
            case "stories" -> storyService.deleteStory(id);
            case "reviews" -> reviewService.deleteReview(id);
            case "team" -> teamService.deleteTeamMember(id);
            case "pricing" -> pricingPlanService.deletePricing(id);
            default -> throw new ResourceNotFoundException("Section not found: " + section);
        }

        return inertia.redirect("/" + locale + "/admin/marketing/" + section);
    }

    @PostMapping("/{section}/{id}/toggle-publish")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> togglePublish(@PathVariable String locale,
                                                @PathVariable String section,
                                                @PathVariable Long id) {
        switch (section) {
            case "articles" -> articleService.togglePublish(id);
            case "stories" -> storyService.togglePublish(id);
            case "reviews" -> reviewService.togglePublish(id);
            case "team" -> teamService.togglePublish(id);
            default -> throw new ResourceNotFoundException("Section not found: " + section);
        }

        return inertia.redirect("/" + locale + "/admin/marketing/" + section);
    }

    @PostMapping("/{section}/{id}/toggle-homepage")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> toggleHomepage(@PathVariable String locale,
                                                 @PathVariable String section,
                                                 @PathVariable Long id) {
        switch (section) {
            case "articles" -> articleService.toggleArticleHomepageVisibility(id);
            case "stories" -> storyService.toggleStoryHomepageVisibility(id);
            case "reviews" -> reviewService.toggleReviewHomepageVisibility(id);
            case "team" -> teamService.toggleTeamMemberHomepageVisibility(id);
            default -> throw new ResourceNotFoundException("Section not found: " + section);
        }

        return inertia.redirect("/" + locale + "/admin/marketing/home-components");
    }

    @PutMapping("/{section}/{id}/display-order")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateDisplayOrder(@PathVariable String locale,
                                                     @PathVariable String section,
                                                     @PathVariable Long id,
                                                     @RequestBody Map<String, Integer> request) {
        Integer displayOrder = request.get("display_order");

        switch (section) {
            case "articles" -> articleService.updateArticleDisplayOrder(id, displayOrder);
            case "stories" -> storyService.updateStoryDisplayOrder(id, displayOrder);
            case "reviews" -> reviewService.updateReviewDisplayOrder(id, displayOrder);
            case "team" -> teamService.updateTeamMemberDisplayOrder(id, displayOrder);
            default -> throw new ResourceNotFoundException("Section not found: " + section);
        }

        return ResponseEntity.ok().build();
    }
}
