package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.dto.request.ResidentRequest;
import com.bluemoon.apartment.dto.response.ResidentResponse;
import com.bluemoon.apartment.entity.Resident;
import com.bluemoon.apartment.mapper.ResidentMapper;
import com.bluemoon.apartment.repository.HouseholdMemberRepository;
import com.bluemoon.apartment.repository.HouseholdRepository;
import com.bluemoon.apartment.repository.ResidentRepository;
import com.bluemoon.apartment.repository.TemporaryResidenceRepository;
import com.bluemoon.apartment.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdRepository householdRepository;
    private final TemporaryResidenceRepository temporaryResidenceRepository;

    @Override
    public List<ResidentResponse> getAllResidents() {
        return residentRepository.findAll()
                .stream()
                .map(residentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResidentResponse getResidentById(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân khẩu"));

        return residentMapper.toResponse(resident);
    }

    @Override
    public ResidentResponse saveResident(ResidentRequest residentRequest) {
        if (residentRepository.existsByCitizenId(residentRequest.getCitizenId())) {
            throw new IllegalArgumentException("CCCD đã tồn tại");
        }

        Resident resident = residentMapper.toEntity(residentRequest);
        Resident savedResident = residentRepository.save(resident);

        return residentMapper.toResponse(savedResident);
    }

    @Override
    public ResidentResponse updateResident(Long id, ResidentRequest residentRequest) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân khẩu"));

        residentMapper.updateEntity(resident, residentRequest);
        Resident updatedResident = residentRepository.save(resident);

        return residentMapper.toResponse(updatedResident);
    }

    @Override
    @Transactional
    public void deleteResident(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân khẩu"));

        if (householdRepository.existsByHeadResident_Id(id)) {
            throw new IllegalArgumentException("Không thể xóa nhân khẩu này vì đang là chủ hộ của một hộ khẩu.");
        }

        if (householdMemberRepository.existsByResident_Id(id)) {
            throw new IllegalArgumentException("Không thể xóa nhân khẩu này vì đang thuộc một hộ khẩu. Hãy xóa nhân khẩu khỏi hộ khẩu trước.");
        }

        if (temporaryResidenceRepository.existsByResident_Id(id)) {
            throw new IllegalArgumentException("Không thể xóa nhân khẩu này vì đang có hồ sơ tạm trú/tạm vắng.");
        }

        residentRepository.delete(resident);
    }

    @Override
    public List<ResidentResponse> searchResidents(String keyword) {
        return residentRepository
                .findByFullNameContainingIgnoreCaseOrCitizenIdContainingOrPhoneNumberContaining(
                        keyword,
                        keyword,
                        keyword
                )
                .stream()
                .map(residentMapper::toResponse)
                .collect(Collectors.toList());
    }
}