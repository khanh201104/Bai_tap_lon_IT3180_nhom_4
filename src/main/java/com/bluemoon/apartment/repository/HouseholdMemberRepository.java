package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.HouseholdMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseholdMemberRepository extends JpaRepository<HouseholdMember, Long> {

    List<HouseholdMember> findByHousehold_Id(Long householdId);

    boolean existsByHousehold_IdAndResident_Id(Long householdId, Long residentId);

    boolean existsByResident_Id(Long residentId);

    boolean existsByResident_IdAndHousehold_IdNot(Long residentId, Long householdId);

    void deleteByHousehold_Id(Long householdId);

    int countMemberByHouseholdId(Long householdId);

}