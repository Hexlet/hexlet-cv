package io.hexlet.cv.service;

import io.hexlet.cv.dto.pagesection.PageSectionCreateDTO;
import io.hexlet.cv.dto.pagesection.PageSectionDTO;
import io.hexlet.cv.dto.pagesection.PageSectionUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.PageSectionMapper;
import io.hexlet.cv.repository.PageSectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PageSectionService {
    private final PageSectionRepository repository;
    private final PageSectionMapper mapper;

    public void setActivenessById(Long id, boolean isActive) {

        var model = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция с id %d не найдена", id)));

        model.setActive(isActive);
        repository.save(model);
    }

    public List<PageSectionDTO> findAll() {
        return repository.findAll().stream()
            .map(mapper::map)
            .toList();
    }

    public PageSectionDTO findById(Long id) {

        var model = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция с id %d не найдена", id)));

        return mapper.map(model);
    }

    public PageSectionDTO findByKey(String key) {

        var model = repository.findBySectionKey(key)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция \"%s\" не найдена", key)));

        return mapper.map(model);
    }

    public PageSectionDTO create(PageSectionCreateDTO dto) {

        // По умолчанию секция включена, если не указано другое
        dto.setActive(dto.getActive() != null ? dto.getActive() : true);

        var model = mapper.map(dto);
        repository.save(model);
        return mapper.map(model);
    }

    public PageSectionDTO updateById(PageSectionUpdateDTO dto, Long id) {

        var model = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция с id %d не найдена", id)));

        mapper.update(dto, model);
        repository.save(model);
        return mapper.map(model);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
