package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.marketing.ArticleCreateDto;
import io.hexlet.cv.dto.marketing.ArticleDto;
import io.hexlet.cv.dto.marketing.ArticleUpdateDto;
import io.hexlet.cv.dto.marketing.PricingCreateDto;
import io.hexlet.cv.dto.marketing.PricingDto;
import io.hexlet.cv.dto.marketing.PricingUpdateDto;
import io.hexlet.cv.dto.marketing.ReviewCreateDto;
import io.hexlet.cv.dto.marketing.ReviewDto;
import io.hexlet.cv.dto.marketing.ReviewUpdateDto;
import io.hexlet.cv.dto.marketing.StoryCreateDto;
import io.hexlet.cv.dto.marketing.StoryDto;
import io.hexlet.cv.dto.marketing.StoryUpdateDto;
import io.hexlet.cv.dto.marketing.TeamCreateDto;
import io.hexlet.cv.dto.marketing.TeamDto;
import io.hexlet.cv.dto.marketing.TeamUpdateDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.model.enums.TeamMemberType;
import io.hexlet.cv.model.enums.TeamPosition;
import io.hexlet.cv.service.ArticleService;
import io.hexlet.cv.service.PricingPlanService;
import io.hexlet.cv.service.ReviewService;
import io.hexlet.cv.service.StoryService;
import io.hexlet.cv.service.TeamService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@Slf4j
@AllArgsConstructor
@RequestMapping("/admin/marketing")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMarketingController {

    private final Inertia inertia;
    private final ArticleService articleService;
    private final StoryService storyService;
    private final ReviewService reviewService;
    private final TeamService teamService;
    private final PricingPlanService pricingPlanService;

    @GetMapping("/{section}")
    public ResponseEntity<String> index(@PathVariable String section,
                                        @PageableDefault(size = 20) Pageable pageable) {
        log.debug("[MARKETING] Getting section: {} for locale: {}", section, pageable);

        return switch (section) {
            case "articles" -> {
                Page<ArticleDto> articlesPage = articleService.getAllArticles(pageable);
                Map<String, Object> props = Map.of(
                        "articles", articlesPage.getContent(),
                        "pagination", Map.of(
                                "currentPage", articlesPage.getNumber(),
                                "totalPages", articlesPage.getTotalPages(),
                                "totalElements", articlesPage.getTotalElements(),
                                "pageSize", pageable.getPageSize()
                        ),
                        "activeMainSection", "marketing",
                        "activeSubSection", "articles"
                );
                log.debug("[MARKETING] Rendering articles page with {} items",
                        articlesPage.getContent().size());
                yield inertia.render("Admin/Marketing/Articles/Index", props);
            }
            case "stories" -> {
                Page<StoryDto> storiesPage = storyService.getAllStories(pageable);
                Map<String, Object> props = Map.of(
                        "stories", storiesPage.getContent(),
                        "pagination", Map.of(
                                "currentPage", storiesPage.getNumber(),
                                "totalPages", storiesPage.getTotalPages(),
                                "totalElements", storiesPage.getTotalElements(),
                                "pageSize", pageable.getPageSize()
                        ),
                        "activeMainSection", "marketing",
                        "activeSubSection", "stories"
                );
                log.debug("[MARKETING] Rendering stories page with {} items",
                        storiesPage.getContent().size());
                yield inertia.render("Admin/Marketing/Stories/Index", props);
            }
            case "reviews" -> {
                Page<ReviewDto> reviewsPage = reviewService.getAllReviews(pageable);
                Map<String, Object> props = Map.of(
                        "reviews", reviewsPage.getContent(),
                        "pagination", Map.of(
                                "currentPage", reviewsPage.getNumber(),
                                "totalPages", reviewsPage.getTotalPages(),
                                "totalElements", reviewsPage.getTotalElements(),
                                "pageSize", pageable.getPageSize()

                        ),
                        "activeMainSection", "marketing",
                        "activeSubSection", "reviews"
                );
                log.debug("[MARKETING] Rendering reviews page with {} items",
                        reviewsPage.getContent().size());
                yield inertia.render("Admin/Marketing/Reviews/Index", props);
            }
            case "team" -> {
                Page<TeamDto> teamsPage = teamService.getAllTeamMembers(pageable);
                Map<String, Object> props = Map.of(
                        "team", teamsPage.getContent(),
                        "pagination", Map.of(
                                "currentPage", teamsPage.getNumber(),
                                "totalPages", teamsPage.getTotalPages(),
                                "totalElements", teamsPage.getTotalElements(),
                                "pageSize", pageable.getPageSize()
                        ),
                        "activeMainSection", "marketing",
                        "activeSubSection", "team"
                );
                log.debug("[MARKETING] Rendering teams page with {} items",
                        teamsPage.getContent().size());
                yield inertia.render("Admin/Marketing/Team/Index", props);
            }
            case "pricing" -> {
                Page<PricingDto> pricingPage = pricingPlanService.getAllPricing(pageable);
                Map<String, Object> props = Map.of(
                        "pricing", pricingPage.getContent(),
                        "pagination", Map.of(
                                "currentPage", pricingPage.getNumber(),
                                "totalPages", pricingPage.getTotalPages(),
                                "totalElements", pricingPage.getTotalElements(),
                                "pageSize", pageable.getPageSize()
                        ),
                        "activeMainSection", "marketing",
                        "activeSubSection", "pricing"
                );
                log.debug("[MARKETING] Rendering pricing page with {} items",
                        pricingPage.getContent().size());
                yield inertia.render("Admin/Marketing/Pricing/Index", props);
            }
            case "home-components" -> {
                Map<String, Object> props = Map.of(
                        "articles", articleService.getHomepageArticles(),
                        "stories", storyService.getHomepageStories(),
                        "reviews", reviewService.getHomepageReviews(),
                        "team", teamService.getHomepageTeamMembers(),
                        "activeMainSection", "marketing",
                        "activeSubSection", "home-components"
                );
                log.debug("[MARKETING] Rendering home components page");
                yield inertia.render("Admin/Marketing/HomeComponents/Index", props);
            }
            default -> throw new ResourceNotFoundException("section.not.found");
        };
    }

    @GetMapping("/")
    public ResponseEntity<String> defaultSection() {
        log.debug("[MARKETING] Redirect to default section");
        return inertia.redirect("/admin/marketing/articles");
    }

    @GetMapping("/{section}/create")
    public ResponseEntity<String> createForm(@PathVariable String section) {
        Map<String, Object> props = Map.of(
                "activeMainSection", "marketing",
                "activeSubSection", section
        );

        return switch (section) {
            case "articles" -> inertia.render("Admin/Marketing/Articles/Create", props);
            case "stories" -> inertia.render("Admin/Marketing/Stories/Create", props);
            case "reviews" -> inertia.render("Admin/Marketing/Reviews/Create", props);
            case "team" -> {
                Map<String, Object> teamProps = new HashMap<>(props);
                teamProps.put("positions", TeamPosition.values());
                teamProps.put("memberTypes", TeamMemberType.values());
                yield inertia.render("Admin/Marketing/Team/Create", teamProps);
            }
            case "pricing" -> inertia.render("Admin/Marketing/Pricing/Create", props);
            default -> throw new ResourceNotFoundException("Create form not found for section: " + section);
        };
    }

    @GetMapping("/{section}/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> editForm(@PathVariable String section, @PathVariable Long id) {
        log.debug("[MARKETING] Edit form for {} with id: {}", section, id);

        Map<String, Object> baseProps = Map.of(
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
                props.put("positions", TeamPosition.values());
                props.put("memberTypes", TeamMemberType.values());
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

    @PostMapping("/articles")
    public ResponseEntity<String> createArticle(@Valid @RequestBody ArticleCreateDto createDTO) {
        log.debug("[MARKETING] Creating article");

        articleService.createArticle(createDTO);
        return inertia.redirect("/admin/marketing/articles");
    }

    @PostMapping("/stories")
    public ResponseEntity<String> createStory(@Valid @RequestBody StoryCreateDto createDTO) {
        log.debug("[MARKETING] Creating story");

        storyService.createStory(createDTO);
        return inertia.redirect("/admin/marketing/stories");
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewCreateDto createDTO) {
        log.debug("[MARKETING] Creating review");

        reviewService.createReview(createDTO);
        return inertia.redirect("/admin/marketing/reviews");
    }

    @PostMapping("/team")
    public ResponseEntity<String> createTeamMember(@Valid @RequestBody TeamCreateDto createDTO) {
        log.debug("[MARKETING] Creating team member");

        teamService.createTeamMember(createDTO);
        return inertia.redirect("/admin/marketing/team");
    }

    @PostMapping("/pricing")
    public ResponseEntity<String> createPricing(@Valid @RequestBody PricingCreateDto createDTO) {
        log.debug("[MARKETING] Creating pricing plan");

        pricingPlanService.createPricing(createDTO);
        return inertia.redirect("/admin/marketing/pricing");
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable Long id,
                                                @Valid @RequestBody ArticleUpdateDto updateDTO) {
        log.debug("[MARKETING] Updating article id: {}", id);

        articleService.updateArticle(id, updateDTO);
        return inertia.redirect("/admin/marketing/articles");
    }

    @PutMapping("/stories/{id}")
    public ResponseEntity<String> updateStory(@PathVariable Long id,
                                              @Valid @RequestBody StoryUpdateDto updateDTO) {
        log.debug("[MARKETING] Updating story id: {}", id);

        storyService.updateStory(id, updateDTO);
        return inertia.redirect("/admin/marketing/stories");
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<String> updateReview(@PathVariable Long id,
                                               @Valid @RequestBody ReviewUpdateDto updateDTO) {
        log.debug("[MARKETING] Updating review id: {}", id);

        reviewService.updateReview(id, updateDTO);
        return inertia.redirect("/admin/marketing/reviews");
    }

    @PutMapping("/team/{id}")
    public ResponseEntity<String> updateTeamMember(@PathVariable Long id,
                                                   @Valid @RequestBody TeamUpdateDto updateDTO) {
        log.debug("[MARKETING] Updating team member id: {}", id);

        teamService.updateTeamMember(id, updateDTO);
        return inertia.redirect("/admin/marketing/team");
    }

    @PutMapping("/pricing/{id}")
    public ResponseEntity<String> updatePricing(@PathVariable Long id,
                                                @Valid @RequestBody PricingUpdateDto updateDTO) {
        log.debug("[MARKETING] Updating pricing plan id: {}", id);

        pricingPlanService.updatePricing(id, updateDTO);
        return inertia.redirect("/admin/marketing/pricing");
    }

    @DeleteMapping("/{section}/{id}")
    public ResponseEntity<String> delete(@PathVariable String section,
                                         @PathVariable Long id) {
        log.debug("[MARKETING] Deleting {} with id: {}", section, id);

        switch (section) {
            case "articles" -> articleService.deleteArticle(id);
            case "stories" -> storyService.deleteStory(id);
            case "reviews" -> reviewService.deleteReview(id);
            case "team" -> teamService.deleteTeamMember(id);
            case "pricing" -> pricingPlanService.deletePricing(id);
            default -> throw new ResourceNotFoundException("Section not found: " + section);
        }

        return inertia.redirect("/admin/marketing/" + section);
    }

    @PostMapping("/{section}/{id}/toggle-publish")
    public ResponseEntity<String> togglePublish(@PathVariable String section,
                                                @PathVariable Long id) {
        log.debug("[MARKETING] Toggling publish for {} id: {}", section, id);

        switch (section) {
            case "articles" -> articleService.togglePublish(id);
            case "stories" -> storyService.togglePublish(id);
            case "reviews" -> reviewService.togglePublish(id);
            case "team" -> teamService.togglePublish(id);
            default -> throw new ResourceNotFoundException("Section not found: " + section);
        }

        return inertia.redirect("/admin/marketing/" + section);
    }

    @PostMapping("/{section}/{id}/toggle-homepage")
    public ResponseEntity<String> toggleHomepage(@PathVariable String section,
                                                 @PathVariable Long id) {
        log.debug("[MARKETING] Toggling homepage for {} id: {}", section, id);

        switch (section) {
            case "articles" -> articleService.toggleArticleHomepageVisibility(id);
            case "stories" -> storyService.toggleStoryHomepageVisibility(id);
            case "reviews" -> reviewService.toggleReviewHomepageVisibility(id);
            case "team" -> teamService.toggleTeamMemberHomepageVisibility(id);
            default -> throw new ResourceNotFoundException("Section not found: " + section);
        }

        return inertia.redirect("/admin/marketing/home-components");
    }

    @PutMapping("/{section}/{id}/display-order")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateDisplayOrder(@PathVariable String section,
                                                     @PathVariable Long id,
                                                     @RequestBody Map<String, Integer> request) {
        log.debug("[MARKETING] Updating display order for {} id: {}", section, id);

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
