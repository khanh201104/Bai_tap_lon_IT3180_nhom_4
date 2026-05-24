package com.bluemoon.apartment.service;

import com.bluemoon.apartment.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> findAll();

    Optional<Vehicle> findById(Long id);

    Vehicle save(Vehicle vehicle);

    void deleteById(Long id);

    List<Vehicle> search(String plateOrApartment);
}
