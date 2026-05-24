package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.entity.Vehicle;
import com.bluemoon.apartment.repository.VehicleRepository;
import com.bluemoon.apartment.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> search(String plateOrApartment) {
        if (plateOrApartment == null || plateOrApartment.isBlank()) {
            return findAll();
        }
        String q = plateOrApartment.trim();
        Map<Long, Vehicle> map = new LinkedHashMap<>();
        vehicleRepository.findByPlateContainingIgnoreCase(q).forEach(v -> map.put(v.getId(), v));
        vehicleRepository.findByApartmentCodeContainingIgnoreCase(q).forEach(v -> map.put(v.getId(), v));
        return new ArrayList<>(map.values());
    }
}
