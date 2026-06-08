package com.bluemoon.apartment.service;

import com.bluemoon.apartment.dto.request.HouseholdRequest;
import com.bluemoon.apartment.dto.response.HouseholdResponse;

import java.util.List;

public interface HouseholdService {

    List<HouseholdResponse> getAll();

    List<HouseholdResponse> search(String keyword);

    HouseholdResponse getById(Long id);

    void save(HouseholdRequest request);

    void update(Long id, HouseholdRequest request);

    void delete(Long id);
}