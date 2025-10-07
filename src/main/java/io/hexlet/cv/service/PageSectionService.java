package io.hexlet.cv.service;

import io.hexlet.cv.dto.pagesection.PageSectionCreateDTO;
import io.hexlet.cv.dto.pagesection.PageSectionDTO;
import io.hexlet.cv.dto.pagesection.PageSectionUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.PageSectionMapper;
import io.hexlet.cv.repository.PageSectionRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PageSectionService {
    private final PageSectionRepository repository;
    private final PageSectionMapper mapper;

    public List<PageSectionDTO> findAll() {
        return repository.findAll().stream()
            .map(mapper::map)
            .toList();
    }

    public List<PageSectionDTO> findByPageKey(String key) {

        var models = repository.findByPageKey(key);

        if (models.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Секции страницы \"%s\" не найдены", key));
        }

        return models.stream()
            .map(mapper::map)
            .toList();
    }

    public List<PageSectionDTO> findActiveByPageKey(String key) {
        return repository.findByPageKeyAndActiveTrue(key).stream()
            .map(mapper::map)
            .toList();
    }

    public PageSectionDTO findByPageKeyAndSectionKey(String pageKey, String sectionKey) {

        var model = repository.findBySectionKey(sectionKey)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Секция \"%s\" на странице \"%s\" не найдена", pageKey, sectionKey)
            ));

        return mapper.map(model);
    }

    public PageSectionDTO createForPage(String pageKey, PageSectionCreateDTO dto) {

        if (repository.existsByPageKeyAndSectionKey(pageKey, dto.getSectionKey())) {
            throw new EntityExistsException(
                String.format("Секция \"%s\" на странице \"%s\" уже существует", dto.getSectionKey(), pageKey)
            );
        }

        var model = mapper.map(dto);
        model.setPageKey(pageKey);
        repository.save(model);
        return mapper.map(model);
    }

    public PageSectionDTO updateByPageKeyAndSectionKey(String pageKey, String sectionKey, PageSectionUpdateDTO dto) {

        var model = repository.findByPageKeyAndSectionKey(pageKey, sectionKey)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Секция \"%s\" на странице \"%s\" не найдена", sectionKey, pageKey)
            ));

        var newSectionKey = dto.getSectionKey().get();

        if (!newSectionKey.equals(model.getSectionKey())
            && repository.existsByPageKeyAndSectionKey(pageKey, newSectionKey)) {

            throw new EntityExistsException(
                String.format("Секция \"%s\" на странице \"%s\" уже существует", newSectionKey, pageKey)
            );
        }

        mapper.update(dto, model);
        repository.save(model);
        return mapper.map(model);
    }

    public void deleteByPageKeyAndSectionKey(String pageKey, String sectionKey) {
        repository.deleteByPageKeyAndSectionKey(pageKey, sectionKey);
    }

//    public PageSectionDTO findById(Long id) {
//
//        var model = repository.findById(id)
//            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция с id %d не найдена", id)));
//
//        return mapper.map(model);
//    }
//
//    public PageSectionDTO updateById(PageSectionUpdateDTO dto, Long id) {
//
//        var model = repository.findById(id)
//            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция с id %d не найдена", id)));
//
//        mapper.update(dto, model);
//        repository.save(model);
//        return mapper.map(model);
//    }
//
//    public void deleteById(Long id) {
//        repository.deleteById(id);
//    }
}
