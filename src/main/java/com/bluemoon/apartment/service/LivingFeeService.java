package com.bluemoon.apartment.service;

import com.bluemoon.apartment.entity.LivingFee;

import java.util.List;
import java.util.Optional;

public interface LivingFeeService {
    List<LivingFee> findAll();

    Optional<LivingFee> findById(Long id);

    LivingFee save(LivingFee livingFee);

    void deleteById(Long id);

    double sumByHousehold(Long householdId);
}
