package io.hexlet.cv.service;

import io.hexlet.cv.converter.PricingConverter;
import io.hexlet.cv.dto.marketing.PricingCreateDto;
import io.hexlet.cv.dto.marketing.PricingDto;
import io.hexlet.cv.dto.marketing.PricingUpdateDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.PricingMapper;
import io.hexlet.cv.repository.PricingPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PricingPlanService {

    private final PricingPlanRepository pricingRepository;
    private final PricingMapper pricingMapper;
    private final PricingConverter pricingConverter;

    public Page<PricingDto> getAllPricing(Pageable pageable) {
        return pricingRepository.findAllByOrderByIdAsc(pageable)
                .map(pricingConverter::convert);
    }

    public PricingDto getPricingById(Long id) {
        var pricing = pricingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pricing.plan.not.found"));
        return pricingConverter.convert(pricing);
    }

    @Transactional
    public PricingDto createPricing(PricingCreateDto createDTO) {
        if (pricingRepository.existsByName(createDTO.getName())) {
            throw new IllegalArgumentException("pricing.plan.with.this.name.already.exists");
        }

        var pricing = pricingMapper.map(createDTO);
        pricing.calculateFinalPrice();

        var savedPricing = pricingRepository.save(pricing);
        return pricingConverter.convert(savedPricing);
    }

    @Transactional
    public PricingDto updatePricing(Long id, PricingUpdateDto updateDTO) {
        var pricing = pricingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pricing.plan.not.found"));

        pricingMapper.update(updateDTO, pricing);

        pricing.calculateFinalPrice();

        var savedPricing = pricingRepository.save(pricing);
        return pricingConverter.convert(savedPricing);
    }

    @Transactional
    public void deletePricing(Long id) {
        if (!pricingRepository.existsById(id)) {
            throw new ResourceNotFoundException("pricing.plan.not.found");
        }
        pricingRepository.deleteById(id);
    }
}
