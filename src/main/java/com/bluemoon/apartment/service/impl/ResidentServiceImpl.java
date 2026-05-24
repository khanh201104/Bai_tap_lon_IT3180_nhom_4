package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.dto.request.ResidentRequest;
import com.bluemoon.apartment.dto.response.ResidentResponse;
import com.bluemoon.apartment.entity.Resident;
import com.bluemoon.apartment.mapper.ResidentMapper;
import com.bluemoon.apartment.repository.ResidentRepository;
import com.bluemoon.apartment.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;

    @Override
    public List<ResidentResponse> getAllResidents() {
        return residentRepository.findAll().stream()
                .map(residentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResidentResponse getResidentById(Long id) {
        Resident resident = residentRepository.findById(id).orElse(null);
        return residentMapper.toResponse(resident);
    }

    @Override
    public ResidentResponse saveResident(ResidentRequest residentRequest) {
        Resident resident = residentMapper.toEntity(residentRequest);
        Resident savedResident = residentRepository.save(resident);
        return residentMapper.toResponse(savedResident);
    }

    @Override
    public ResidentResponse updateResident(Long id, ResidentRequest residentRequest) {
        Resident resident = residentRepository.findById(id).orElse(null);
        if (resident != null) {
            residentMapper.updateEntity(resident, residentRequest);
            Resident updatedResident = residentRepository.save(resident);
            return residentMapper.toResponse(updatedResident);
        }
        return null;
    }

    @Override
    public void deleteResident(Long id) {
        residentRepository.deleteById(id);
    }

    @Override
    public List<ResidentResponse> searchResidents(String keyword) {
        return residentRepository.findByFullNameContainingIgnoreCaseOrCitizenIdContainingOrPhoneNumberContaining(
                keyword, keyword, keyword).stream()
                .map(residentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
