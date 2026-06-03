package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.HouseholdMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdMemberRepository
        extends JpaRepository<HouseholdMember, Long> {
}