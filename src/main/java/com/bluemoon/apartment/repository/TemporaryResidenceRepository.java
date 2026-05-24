package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.TemporaryResidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemporaryResidenceRepository extends JpaRepository<TemporaryResidence, Long> {
    List<TemporaryResidence> findByResidentFullNameContainingIgnoreCase(String fullName);
}
