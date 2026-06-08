package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.constant.VehicleStatus;
import com.bluemoon.apartment.constant.VehicleType;
import com.bluemoon.apartment.dto.request.VehicleRequest;
import com.bluemoon.apartment.dto.response.VehicleResponse;
import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.entity.Vehicle;
import com.bluemoon.apartment.mapper.VehicleMapper;
import com.bluemoon.apartment.repository.HouseholdRepository;
import com.bluemoon.apartment.repository.VehicleRepository;
import com.bluemoon.apartment.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final HouseholdRepository householdRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponse> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponse> search(String keyword, Long householdId, VehicleType vehicleType) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();

        return vehicleRepository.findAll()
                .stream()
                .filter(vehicle -> normalizedKeyword.isEmpty()
                        || containsIgnoreCase(vehicle.getLicensePlate(), normalizedKeyword)
                        || containsIgnoreCase(vehicle.getOwnerName(), normalizedKeyword)
                        || containsIgnoreCase(vehicle.getHousehold() != null ? vehicle.getHousehold().getApartmentNumber() : null, normalizedKeyword))
                .filter(vehicle -> householdId == null
                        || vehicle.getHousehold() != null && vehicle.getHousehold().getId().equals(householdId))
                .filter(vehicle -> vehicleType == null || vehicle.getVehicleType() == vehicleType)
                .map(vehicleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponse getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phương tiện"));

        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    @Transactional
    public void save(VehicleRequest request) {
        String licensePlate = normalizeLicensePlate(request.getLicensePlate());

        if (vehicleRepository.existsByLicensePlateIgnoreCase(licensePlate)) {
            throw new IllegalArgumentException("Biển số xe đã tồn tại");
        }

        Household household = householdRepository.findById(request.getHouseholdId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        Vehicle vehicle = vehicleMapper.toEntity(request);
        vehicle.setLicensePlate(licensePlate);
        vehicle.setHousehold(household);

        if (vehicle.getRegisteredDate() == null) {
            vehicle.setRegisteredDate(LocalDate.now());
        }

        if (vehicle.getStatus() == null) {
            vehicle.setStatus(VehicleStatus.ACTIVE);
        }

        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void update(Long id, VehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phương tiện"));

        String licensePlate = normalizeLicensePlate(request.getLicensePlate());

        if (vehicleRepository.existsByLicensePlateIgnoreCaseAndIdNot(licensePlate, id)) {
            throw new IllegalArgumentException("Biển số xe đã tồn tại");
        }

        Household household = householdRepository.findById(request.getHouseholdId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        vehicleMapper.updateEntity(vehicle, request);
        vehicle.setLicensePlate(licensePlate);
        vehicle.setHousehold(household);

        if (vehicle.getRegisteredDate() == null) {
            vehicle.setRegisteredDate(LocalDate.now());
        }

        if (vehicle.getStatus() == null) {
            vehicle.setStatus(VehicleStatus.ACTIVE);
        }

        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phương tiện"));

        vehicleRepository.delete(vehicle);
    }

    private String normalizeLicensePlate(String licensePlate) {
        if (licensePlate == null) {
            return "";
        }

        return licensePlate.trim().replaceAll("\\s+", " ").toUpperCase();
    }

    private boolean containsIgnoreCase(String source, String keyword) {
        return source != null && source.toLowerCase().contains(keyword);
    }
}