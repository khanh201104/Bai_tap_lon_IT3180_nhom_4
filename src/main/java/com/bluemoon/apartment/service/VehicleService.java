package com.bluemoon.apartment.service;

import com.bluemoon.apartment.constant.VehicleType;
import com.bluemoon.apartment.dto.request.VehicleRequest;
import com.bluemoon.apartment.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleService {

    List<VehicleResponse> getAll();

    List<VehicleResponse> search(String keyword, Long householdId, VehicleType vehicleType);

    VehicleResponse getById(Long id);

    void save(VehicleRequest request);

    void update(Long id, VehicleRequest request);

    void delete(Long id);
}