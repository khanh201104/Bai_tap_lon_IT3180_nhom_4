package com.bluemoon.apartment.service;

import com.bluemoon.apartment.dto.request.TemporaryResidenceRequest;
import com.bluemoon.apartment.dto.response.TemporaryResidenceResponse;

import java.util.List;

public interface TemporaryResidenceService {

    List<TemporaryResidenceResponse> getAll();

    TemporaryResidenceResponse getById(Long id);

    TemporaryResidenceResponse save(TemporaryResidenceRequest request);

    TemporaryResidenceResponse update(Long id, TemporaryResidenceRequest request);

    void delete(Long id);

    List<TemporaryResidenceResponse> searchByResidentName(String name);
}