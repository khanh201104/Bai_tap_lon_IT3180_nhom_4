package com.bluemoon.apartment.service;

import com.bluemoon.apartment.dto.request.FeeTypeRequest;
import com.bluemoon.apartment.dto.response.FeeTypeResponse;

import java.util.List;

public interface FeeTypeService {

    List<FeeTypeResponse> getAll();

    List<FeeTypeResponse> getActiveFeeTypes();

    List<FeeTypeResponse> search(String keyword);

    FeeTypeResponse getById(Long id);

    void save(FeeTypeRequest request);

    void update(Long id, FeeTypeRequest request);

    void delete(Long id);
}