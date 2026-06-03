package com.bluemoon.apartment.service;

import com.bluemoon.apartment.entity.Household;

import java.util.List;

public interface HouseholdService {

    List<Household> getAll();

    Household getById(Long id);

    Household save(Household household);

    void delete(Long id);

    List<Household> searchByHeadName(String headName);
}
