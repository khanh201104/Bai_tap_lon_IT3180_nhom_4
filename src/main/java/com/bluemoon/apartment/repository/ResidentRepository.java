package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    List<Resident> findByFullNameContainingIgnoreCaseOrCitizenIdContainingOrPhoneNumberContaining(
            String fullName,
            String citizenId,
            String phoneNumber
    );
    boolean existsByCitizenId(String citizenId);
}