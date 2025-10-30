package io.hexlet.cv.service;

import io.hexlet.cv.dto.admin.WebinarDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.handler.exception.WebinarAlreadyExistsException;
import io.hexlet.cv.mapper.AdminWebinarMapper;
import io.hexlet.cv.repository.WebinarRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminWebinarService {

    private final WebinarRepository webinarRepository;
    private final AdminWebinarMapper adminWebinarMapper;

    @Transactional
    public void createWebinar(WebinarDTO webinar) {

        webinarRepository.findFirstByWebinarNameOrWebinarDateOrWebinarRegLinkOrWebinarRecordLink(
                webinar.getWebinarName(),
                webinar.getWebinarDate(),
                webinar.getWebinarRegLink(),
                webinar.getWebinarRecordLink()
        ).ifPresent( webenarIs -> {
                    throw new WebinarAlreadyExistsException("Вебинар с таким именем уже существует");
       //     if (webenarIs.getWebinarName() != null) {
             // проверка что конкретно есть уже в базе по какому ключу найдено
       //     }

                }
        );

        webinarRepository.save(adminWebinarMapper.map(webinar));
    }

    @Transactional
    public void updateWebinar(Long id, WebinarDTO webinar) {
        var webinarToUpdate = webinarRepository.findById(id).orElseThrow( () ->
                new ResourceNotFoundException("Вебенар не найден")
        );
        var toSave = adminWebinarMapper.map(webinar);
        toSave.setId(id);

        webinarRepository.save(webinarToUpdate);
    }

    @Transactional
    public void deleteWebinar(Long id) {
        webinarRepository.findById(id).orElseThrow( () ->
                new ResourceNotFoundException("Вебенар не найден")
        );
        webinarRepository.deleteById(id);
    }
}
