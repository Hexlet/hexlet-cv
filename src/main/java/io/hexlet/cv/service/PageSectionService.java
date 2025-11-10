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

    public List<PageSectionDTO> findAllOnPage(String pageKey, Boolean isActive) {

        if (pageKey != null && isActive != null) {
            return repository.findByPageKeyAndActive(pageKey, isActive).stream()
                .map(mapper::map)
                .toList();

        } else if (pageKey != null) {
            return repository.findByPageKey(pageKey).stream()
                .map(mapper::map)
                .toList();

        } else if (isActive != null) {
            return repository.findByActive(isActive).stream()
                .map(mapper::map)
                .toList();

        } else {
            return repository.findAll().stream()
                .map(mapper::map)
                .toList();
        }
    }

    public PageSectionDTO findById(Long id) {

        var model = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция с id %d не найдена", id)));

        return mapper.map(model);
    }

    public PageSectionDTO create(PageSectionCreateDTO dto) {

        if (repository.existsByPageKeyAndSectionKey(dto.getPageKey(), dto.getSectionKey())) {
            throw new EntityExistsException(
                String.format("Секция \"%s\" на странице \"%s\" уже существует", dto.getSectionKey(), dto.getPageKey())
            );
        }

        // По умолчанию секция включена, если не указано иное
        if (dto.getActive() == null) {
            dto.setActive(true);
        }

        var model = mapper.map(dto);
        repository.save(model);
        return mapper.map(model);
    }

    public PageSectionDTO updateById(Long id, PageSectionUpdateDTO dto) {

        var model = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Секция с id %d не найдена", id)));

        var mbDuplicate = repository.findByPageKeyAndSectionKey(dto.getPageKey().get(), dto.getSectionKey().get());
        if (mbDuplicate.isPresent() && id != mbDuplicate.get().getId()) {
            throw new EntityExistsException(
                String.format("Секция \"%s\" на странице \"%s\" уже существует", dto.getSectionKey(), dto.getPageKey())
            );
        }

        mapper.update(dto, model);
        repository.save(model);
        return mapper.map(model);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

//    public PageSectionDTO findByPageKeyAndSectionKey(String pageKey, String sectionKey) {
//
//        var model = repository.findByPageKeyAndSectionKey(pageKey, sectionKey)
//            .orElseThrow(() -> new ResourceNotFoundException(
//                String.format("Секция \"%s\" на странице \"%s\" не найдена", pageKey, sectionKey)
//            ));
//
//        return mapper.map(model);
//    }
//
//    public PageSectionDTO updateByPageKeyAndSectionKey(String pageKey, String sectionKey, PageSectionUpdateDTO dto) {
//
//        var model = repository.findByPageKeyAndSectionKey(pageKey, sectionKey)
//            .orElseThrow(() -> new ResourceNotFoundException(
//                String.format("Секция \"%s\" на странице \"%s\" не найдена", sectionKey, pageKey)
//            ));
//
//        var newSectionKey = dto.getSectionKey().get();
//
//        if (!newSectionKey.equals(model.getSectionKey())
//            && repository.existsByPageKeyAndSectionKey(pageKey, newSectionKey)) {
//
//            throw new EntityExistsException(
//                String.format("Секция \"%s\" на странице \"%s\" уже существует", newSectionKey, pageKey)
//            );
//        }
//
//        mapper.update(dto, model);
//        repository.save(model);
//        return mapper.map(model);
//    }
//
//    public void deleteByPageKeyAndSectionKey(String pageKey, String sectionKey) {
//        repository.deleteByPageKeyAndSectionKey(pageKey, sectionKey);
//    }
}
