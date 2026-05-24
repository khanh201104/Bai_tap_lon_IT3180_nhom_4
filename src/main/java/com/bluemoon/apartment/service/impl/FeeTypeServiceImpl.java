package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.repository.FeeTypeRepository;
import com.bluemoon.apartment.service.FeeTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeTypeServiceImpl implements FeeTypeService {

    private final FeeTypeRepository feeTypeRepository;

    public FeeTypeServiceImpl(FeeTypeRepository feeTypeRepository) {
        this.feeTypeRepository = feeTypeRepository;
    }

    @Override
    public List<FeeType> findAll() {
        return feeTypeRepository.findAll();
    }

    @Override
    public Optional<FeeType> findById(Long id) {
        return feeTypeRepository.findById(id);
    }

    @Override
    public FeeType save(FeeType feeType) {
        return feeTypeRepository.save(feeType);
    }

    @Override
    public void deleteById(Long id) {
        feeTypeRepository.deleteById(id);
    }

    @Override
    public List<FeeType> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        return feeTypeRepository.findByNameContainingIgnoreCase(keyword.trim());
    }
}
