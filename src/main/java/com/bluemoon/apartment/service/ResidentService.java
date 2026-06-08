package com.bluemoon.apartment.service;

import com.bluemoon.apartment.dto.request.ResidentRequest;
import com.bluemoon.apartment.dto.response.ResidentResponse;

import java.util.List;

public interface ResidentService {

    List<ResidentResponse> getAllResidents();

    ResidentResponse getResidentById(Long id);

    ResidentResponse saveResident(ResidentRequest residentRequest);

    ResidentResponse updateResident(Long id, ResidentRequest residentRequest);

    void deleteResident(Long id);

    List<ResidentResponse> searchResidents(String keyword);
}