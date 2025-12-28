package io.hexlet.cv.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/check")
@Slf4j
@RequiredArgsConstructor
public class DebugController {

    @GetMapping("/pagination-test")
    @ResponseBody
    public Map<String, Object> testPagination(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String sort) {

        log.info("=== ТЕСТ ПАГИНАЦИИ ===");
        log.info("Получен Pageable: {}", pageable);
        log.info("Page number: {}", pageable.getPageNumber());
        log.info("Page size: {}", pageable.getPageSize());
        log.info("Sort: {}", pageable.getSort());
        log.info("Has sort: {}", pageable.getSort().isSorted());
        log.info("=== КОНЕЦ ТЕСТА ===");

        return Map.of(
                "success", true,
                "pageable", Map.of(
                        "pageNumber", pageable.getPageNumber(),
                        "pageSize", pageable.getPageSize(),
                        "sort", pageable.getSort().toString(),
                        "offset", pageable.getOffset()
                ),
                "message", "Пагинация работает!"
        );
    }

    @GetMapping("/simulate-data")
    @ResponseBody
    public Map<String, Object> simulateData(
            @PageableDefault(size = 5) Pageable pageable) {

        // Симулируем данные для теста
        log.info("Simulating data with pageable: {}", pageable);

        // Создаем mock страницу
        var data = java.util.List.of(
                Map.of("id", 1, "name", "Item 1"),
                Map.of("id", 2, "name", "Item 2"),
                Map.of("id", 3, "name", "Item 3")
        );

        return Map.of(
                "data", data,
                "pagination", Map.of(
                        "currentPage", pageable.getPageNumber(),
                        "pageSize", pageable.getPageSize(),
                        "hasNext", false
                )
        );
    }
    @GetMapping("/pagination")
    @ResponseBody
    public Map<String, Object> checkPagination() {
        // ... существующий код проверки конфигурации
        return Map.of(
                "loaded", true,
                "message", "Configuration loaded"
        );
    }
}
