package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.constant.RelationshipType;
import com.bluemoon.apartment.dto.request.HouseholdMemberRequest;
import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.entity.HouseholdMember;
import com.bluemoon.apartment.entity.Resident;
import com.bluemoon.apartment.repository.HouseholdMemberRepository;
import com.bluemoon.apartment.repository.HouseholdRepository;
import com.bluemoon.apartment.repository.ResidentRepository;
import com.bluemoon.apartment.service.HouseholdMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseholdMemberServiceImpl implements HouseholdMemberService {

    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final ResidentRepository residentRepository;

    @Override
    @Transactional
    public void addMember(Long householdId, HouseholdMemberRequest request) {
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy cư dân"));

        if (householdMemberRepository.existsByHousehold_IdAndResident_Id(householdId, request.getResidentId())) {
            throw new IllegalArgumentException("Cư dân này đã có trong hộ khẩu này");
        }

        if (householdMemberRepository.existsByResident_IdAndHousehold_IdNot(request.getResidentId(), householdId)) {
            throw new IllegalArgumentException("Cư dân này đã thuộc một hộ khẩu khác");
        }

        if (request.getRelationship() == RelationshipType.HEAD) {
            for (HouseholdMember member : household.getMembers()) {
                if (member.getRelationship() == RelationshipType.HEAD) {
                    member.setRelationship(RelationshipType.OTHER);
                }
            }

            household.setHeadResident(resident);
            householdRepository.save(household);
        }

        HouseholdMember member = HouseholdMember.builder()
                .household(household)
                .resident(resident)
                .relationship(request.getRelationship())
                .joinedDate(request.getJoinedDate())
                .build();

        householdMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void removeMember(Long householdId, Long memberId) {
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        HouseholdMember member = householdMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thành viên hộ khẩu"));

        if (!member.getHousehold().getId().equals(householdId)) {
            throw new IllegalArgumentException("Thành viên không thuộc hộ khẩu này");
        }

        if (member.getRelationship() == RelationshipType.HEAD) {
            throw new IllegalArgumentException("Không thể xóa chủ hộ khỏi hộ khẩu");
        }

        if (household.getHeadResident() != null
                && member.getResident() != null
                && household.getHeadResident().getId().equals(member.getResident().getId())) {
            throw new IllegalArgumentException("Không thể xóa chủ hộ khỏi hộ khẩu");
        }

        householdMemberRepository.delete(member);
    }
}