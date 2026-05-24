package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeeTypeRepository extends JpaRepository<FeeType, Long> {
    List<FeeType> findByNameContainingIgnoreCase(String name);
}
