package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.constant.FeeGroup;
import com.bluemoon.apartment.entity.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeeTypeRepository extends JpaRepository<FeeType, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    Optional<FeeType> findByNameIgnoreCase(String name);

    List<FeeType> findByNameContainingIgnoreCase(String keyword);

    List<FeeType> findByActiveTrue();

    List<FeeType> findByFeeGroupAndActiveTrue(FeeGroup feeGroup);
}