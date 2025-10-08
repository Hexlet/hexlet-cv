package io.hexlet.cv.controller;

import io.hexlet.cv.dto.pagesection.PageSectionCreateDTO;
import io.hexlet.cv.dto.pagesection.PageSectionDTO;
import io.hexlet.cv.dto.pagesection.PageSectionUpdateDTO;
import io.hexlet.cv.service.PageSectionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/pages/sections")
public class PageSectionController {

    private final PageSectionService pageSectionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PageSectionDTO> getAll() {

        return pageSectionService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PageSectionDTO get(@PathVariable Long id) {

        return pageSectionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PageSectionDTO create(@Valid @RequestBody PageSectionCreateDTO dto) {

        return pageSectionService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PageSectionDTO update(@PathVariable Long id,
                                 @Valid @RequestBody PageSectionUpdateDTO dto) {

        return pageSectionService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        pageSectionService.deleteById(id);
    }
}
