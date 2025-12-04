package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.PricingCreateDTO;
import io.hexlet.cv.dto.marketing.PricingDTO;
import io.hexlet.cv.dto.marketing.PricingUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.PricingMapper;
import io.hexlet.cv.model.admin.marketing.PricingPlan;
import io.hexlet.cv.repository.PricingPlanRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PricingPlanService {

    private final PricingPlanRepository pricingRepository;
    private final PricingMapper pricingMapper;

    public List<PricingDTO> getAllPricing() {
        return pricingRepository.findAll()
                .stream()
                .map(pricingMapper::map)
                .collect(Collectors.toList());
    }

    public PricingDTO getPricingById(Long id) {
        var pricing = pricingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing plan not found"));
        return pricingMapper.map(pricing);
    }

    @Transactional
    public PricingDTO createPricing(PricingCreateDTO createDTO) {
        if (pricingRepository.existsByName(createDTO.getName())) {
            throw new IllegalArgumentException("Pricing plan with this name already exists");
        }

        var pricing = pricingMapper.map(createDTO);

        pricing.calculateFinalPrice();

        var savedPricing = pricingRepository.save(pricing);
        return pricingMapper.map(savedPricing);
    }

    @Transactional
    public PricingDTO updatePricing(Long id, PricingUpdateDTO updateDTO) {
        var pricing = pricingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing plan not found"));

        pricingMapper.update(updateDTO, pricing);

        pricing.calculateFinalPrice();

        var savedPricing = pricingRepository.save(pricing);
        return pricingMapper.map(savedPricing);
    }

    @Transactional
    public void deletePricing(Long id) {
        if (!pricingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pricing plan not found");
        }
        pricingRepository.deleteById(id);
    }

    public PricingPlan getPricingPlanEntityById(Long id) {
        return pricingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing plan not found"));
    }
}
