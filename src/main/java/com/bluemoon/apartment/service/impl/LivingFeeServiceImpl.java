package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.entity.LivingFee;
import com.bluemoon.apartment.repository.LivingFeeRepository;
import com.bluemoon.apartment.service.LivingFeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivingFeeServiceImpl implements LivingFeeService {

    private final LivingFeeRepository livingFeeRepository;

    public LivingFeeServiceImpl(LivingFeeRepository livingFeeRepository) {
        this.livingFeeRepository = livingFeeRepository;
    }

    @Override
    public List<LivingFee> findAll() {
        return livingFeeRepository.findAll();
    }

    @Override
    public Optional<LivingFee> findById(Long id) {
        return livingFeeRepository.findById(id);
    }

    @Override
    public LivingFee save(LivingFee livingFee) {
        return livingFeeRepository.save(livingFee);
    }

    @Override
    public void deleteById(Long id) {
        livingFeeRepository.deleteById(id);
    }

    @Override
    public double sumByHousehold(Long householdId) {
        return livingFeeRepository.findByHouseholdId(householdId).stream()
                .mapToDouble(f -> f.getAmount() == null ? 0.0 : f.getAmount())
                .sum();
    }
}
