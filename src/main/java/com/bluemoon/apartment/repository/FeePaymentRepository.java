package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.constant.PaymentStatus;
import com.bluemoon.apartment.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    boolean existsByHousehold_IdAndFeeType_IdAndPeriod(
            Long householdId,
            Long feeTypeId,
            String period
    );

    List<FeePayment> findByHousehold_HouseholdCodeContainingIgnoreCaseOrHousehold_ApartmentNumberContainingIgnoreCaseOrFeeType_NameContainingIgnoreCase(
            String householdCode,
            String apartmentNumber,
            String feeTypeName
    );

    List<FeePayment> findByStatus(PaymentStatus status);

    List<FeePayment> findByPeriod(String period);

    List<FeePayment> findByFeeType_Id(Long feeTypeId);

    void deleteByHousehold_Id(Long householdId);
}