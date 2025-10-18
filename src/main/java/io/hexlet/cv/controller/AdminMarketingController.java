package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.marketing.ArticleCreateDTO;
import io.hexlet.cv.dto.marketing.ArticleUpdateDTO;
import io.hexlet.cv.service.ArticleService;
import jakarta.validation.Valid;
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

import java.util.Map;

@Controller
@RequestMapping("/{locale}/dashboard/marketing/articles")
@AllArgsConstructor
public class AdminMarketingController {

    private final Inertia inertia;
    private final ArticleService articleService;

    // GET /ru/dashboard/marketing/articles - список статей
    @GetMapping
    public ResponseEntity<String> index(@PathVariable String locale) {
        var articles = articleService.getAllArticles();

        return inertia.render("Marketing/Articles/Index",
                Map.of(
                        "locale", locale,
                        "activeSection", "articles",
                        "articles", articles
                ));
    }
    // GET /ru/dashboard/marketing/articles/create - форма создания
    @GetMapping("/create")
    public ResponseEntity<String> createForm(@PathVariable String locale) {
        return inertia.render("Marketing/Articles/Create",
                Map.of(
                        "locale", locale,
                        "activeSection", "articles"
                ));
    }

    // GET /ru/dashboard/marketing/articles/{id}/edit - форма редактирования
    @GetMapping("{id}/edit")
    public ResponseEntity<String> editForm(@PathVariable String locale,
                                             @PathVariable Long id) {
        var article = articleService.getArticleById(id);

        return inertia.render("Marketing/Articles/Edit",
                Map.of(
                        "locale", locale,
                        "activeSection", "articles",
                        "article", article
                ));
    }

    // POST /ru/dashboard/marketing/articles - создание статьи
    @PostMapping
    public ResponseEntity<String> create(@PathVariable String locale,
                                         @Valid @RequestBody ArticleCreateDTO createDTO) {
        articleService.createArticle(createDTO);
        return inertia.redirect("/" + locale + "/dashboard/marketing/articles");
    }

    // PUT /ru/dashboard/marketing/articles/{id} - обновление статьи
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String locale,
                                         @PathVariable Long id,
                                         @Valid @RequestBody ArticleUpdateDTO updateDTO) {
        articleService.updateArticle(id, updateDTO);
        return inertia.redirect("/" + locale + "/dashboard/marketing/articles"
                + id + "/edit");
    }

    // DELETE /ru/dashboard/marketing/articles/{id} - удаление статьи
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String locale,
                                         @PathVariable Long id) {
        articleService.deleteArticle(id);
        return inertia.redirect("/" + locale + "/dashboard/marketing/articles");
    }

}
