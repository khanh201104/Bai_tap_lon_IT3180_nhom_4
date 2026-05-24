package com.bluemoon.apartment.service;

import com.bluemoon.apartment.entity.FeeType;

import java.util.List;
import java.util.Optional;

public interface FeeTypeService {
    List<FeeType> findAll();

    Optional<FeeType> findById(Long id);

    FeeType save(FeeType feeType);

    void deleteById(Long id);

    List<FeeType> searchByName(String keyword);
}
