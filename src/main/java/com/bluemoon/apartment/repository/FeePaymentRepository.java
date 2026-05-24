package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    List<FeePayment> findByHouseholdId(Long householdId);

    List<FeePayment> findByFeeTypeId(Long feeTypeId);

    List<FeePayment> findByStatusIgnoreCase(String status);

    List<FeePayment> findByApartmentCodeContainingIgnoreCase(String apartmentCode);
}
