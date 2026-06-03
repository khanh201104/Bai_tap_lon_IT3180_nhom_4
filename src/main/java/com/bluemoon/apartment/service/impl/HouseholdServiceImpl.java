package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.repository.HouseholdRepository;
import com.bluemoon.apartment.service.HouseholdService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public List<Household> getAll() {
        return householdRepository.findAll();
    }

    @Override
    public Household getById(Long id) {
        return householdRepository.findById(id).orElse(null);
    }

    @Override
    public Household save(Household household) {
        return householdRepository.save(household);
    }

    @Override
    public void delete(Long id) {
        householdRepository.deleteById(id);
    }

    @Override
    public List<Household> searchByHeadName(String headName) {
        return householdRepository.findByHeadNameContaining(headName);
    }
}