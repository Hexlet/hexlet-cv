package io.hexlet.cv.service;

import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.handler.exception.InterviewNotFoundException;
import io.hexlet.cv.mapper.InterviewMapper;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.repository.InterviewRepository;
import io.hexlet.cv.specification.InterviewSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;
    private final InterviewSpecification interviewSpecification;

    public Page<InterviewDTO> getAll(Pageable pageable) {
        return interviewRepository.findAll(pageable)
                .map(interviewMapper::map);
    }

    public Page<InterviewDTO> search(String searchWord, Pageable pageable) {
        Specification<Interview> spec = interviewSpecification.build(searchWord);

        return interviewRepository.findAll(spec, pageable)
                .map(interviewMapper::map);
    }

    public InterviewDTO findById(Long id) {
        Interview foundInterview = interviewRepository.findById(id)
                .orElseThrow(() -> new InterviewNotFoundException("Interview with id: " + id + " not found."));

        return interviewMapper.map(foundInterview);
    }

    public InterviewDTO create(InterviewCreateDTO createDTO) {
        Interview model = interviewMapper.map(createDTO);
        interviewRepository.save(model);
        InterviewDTO dto = interviewMapper.map(model);

        return dto;
    }

    public InterviewDTO update(InterviewUpdateDTO updateDTO, Long id) {
        Interview model = interviewRepository.findById(id)
                .orElseThrow(() -> new InterviewNotFoundException("Interview with id: " + id + " not found."));
        interviewMapper.updateInterview(updateDTO, model);
        interviewRepository.save(model);
        InterviewDTO dto = interviewMapper.map(model);

        return dto;
    }

    public void delete(Long id) {
        interviewRepository.deleteById(id);
    }
}
