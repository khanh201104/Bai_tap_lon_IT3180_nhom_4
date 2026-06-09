package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.constant.RelationshipType;
import com.bluemoon.apartment.dto.request.HouseholdRequest;
import com.bluemoon.apartment.dto.response.HouseholdResponse;
import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.entity.HouseholdMember;
import com.bluemoon.apartment.entity.Resident;
import com.bluemoon.apartment.mapper.HouseholdMapper;
import com.bluemoon.apartment.repository.HouseholdMemberRepository;
import com.bluemoon.apartment.repository.HouseholdRepository;
import com.bluemoon.apartment.repository.ResidentRepository;
import com.bluemoon.apartment.service.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bluemoon.apartment.repository.FeePaymentRepository;
import com.bluemoon.apartment.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final ResidentRepository residentRepository;
    private final HouseholdMapper householdMapper;
    private final FeePaymentRepository feePaymentRepository;
    private final VehicleRepository vehicleRepository;


    @Override
    @Transactional(readOnly = true)
    public List<HouseholdResponse> getAll() {
        return householdRepository.findAll()
                .stream()
                .map(householdMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HouseholdResponse> search(String keyword) {
        return householdRepository
                .findByHouseholdCodeContainingIgnoreCaseOrApartmentNumberContainingIgnoreCaseOrHeadResidentFullNameContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                )
                .stream()
                .map(householdMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HouseholdResponse getById(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        return householdMapper.toResponse(household);
    }

    @Override
    @Transactional
    public void save(HouseholdRequest request) {
        if (householdRepository.existsByHouseholdCode(request.getHouseholdCode())) {
            throw new IllegalArgumentException("Mã hộ khẩu đã tồn tại");
        }

        Resident headResident = residentRepository.findById(request.getHeadResidentId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chủ hộ"));

        if (householdMemberRepository.existsByResident_Id(headResident.getId())) {
            throw new IllegalArgumentException("Cư dân này đã thuộc một hộ khẩu khác");
        }

        Household household = Household.builder()
                .householdCode(request.getHouseholdCode())
                .apartmentNumber(request.getApartmentNumber())
                .address(request.getAddress())
                .headResident(headResident)
                .createdDate(request.getCreatedDate())
                .status(request.getStatus())
                .note(request.getNote())
                .members(new ArrayList<>())
                .apartmentArea(request.getApartmentArea())
                .build();

        ensureHeadMember(household, headResident, request.getCreatedDate());

        householdRepository.save(household);
    }

    @Override
    @Transactional
    public void update(Long id, HouseholdRequest request) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        if (householdRepository.existsByHouseholdCodeAndIdNot(request.getHouseholdCode(), id)) {
            throw new IllegalArgumentException("Mã hộ khẩu đã tồn tại");
        }

        Resident headResident = residentRepository.findById(request.getHeadResidentId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chủ hộ"));

        if (householdMemberRepository.existsByResident_IdAndHousehold_IdNot(headResident.getId(), id)) {
            throw new IllegalArgumentException("Cư dân này đã thuộc một hộ khẩu khác");
        }

        household.setHouseholdCode(request.getHouseholdCode());
        household.setApartmentNumber(request.getApartmentNumber());
        household.setAddress(request.getAddress());
        household.setHeadResident(headResident);
        household.setCreatedDate(request.getCreatedDate());
        household.setStatus(request.getStatus());
        household.setNote(request.getNote());
        household.setApartmentArea(request.getApartmentArea());

        ensureHeadMember(household, headResident, request.getCreatedDate());

        householdRepository.save(household);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        feePaymentRepository.deleteByHousehold_Id(id);
        vehicleRepository.deleteByHousehold_Id(id);
        householdMemberRepository.deleteByHousehold_Id(id);

        householdRepository.delete(household);
    }

    private void ensureHeadMember(Household household, Resident headResident, LocalDate joinedDate) {
        if (household.getMembers() == null) {
            household.setMembers(new ArrayList<>());
        }

        HouseholdMember selectedHeadMember = null;

        for (HouseholdMember member : household.getMembers()) {
            if (member.getRelationship() == RelationshipType.HEAD) {
                member.setRelationship(RelationshipType.OTHER);
            }

            if (member.getResident() != null
                    && member.getResident().getId().equals(headResident.getId())) {
                selectedHeadMember = member;
            }
        }

        if (selectedHeadMember == null) {
            selectedHeadMember = HouseholdMember.builder()
                    .household(household)
                    .resident(headResident)
                    .relationship(RelationshipType.HEAD)
                    .joinedDate(joinedDate)
                    .build();

            household.getMembers().add(selectedHeadMember);
        } else {
            selectedHeadMember.setRelationship(RelationshipType.HEAD);

            if (selectedHeadMember.getJoinedDate() == null) {
                selectedHeadMember.setJoinedDate(joinedDate);
            }
        }
    }
}