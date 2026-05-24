package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.dto.response.StatisticResponse;
import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.repository.FeePaymentRepository;
import com.bluemoon.apartment.repository.FeeTypeRepository;
import com.bluemoon.apartment.repository.LivingFeeRepository;
import com.bluemoon.apartment.repository.VehicleRepository;
import com.bluemoon.apartment.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReportServiceImpl implements ReportService {

    private final FeePaymentRepository feePaymentRepository;
    private final FeeTypeRepository feeTypeRepository;
    private final VehicleRepository vehicleRepository;
    private final LivingFeeRepository livingFeeRepository;

    public ReportServiceImpl(
            FeePaymentRepository feePaymentRepository,
            FeeTypeRepository feeTypeRepository,
            VehicleRepository vehicleRepository,
            LivingFeeRepository livingFeeRepository
    ) {
        this.feePaymentRepository = feePaymentRepository;
        this.feeTypeRepository = feeTypeRepository;
        this.vehicleRepository = vehicleRepository;
        this.livingFeeRepository = livingFeeRepository;
    }

    @Override
    public StatisticResponse buildSummary() {
        Set<Long> householdIds = new HashSet<>();
        feePaymentRepository.findAll().forEach(p -> householdIds.add(p.getHouseholdId()));
        vehicleRepository.findAll().forEach(v -> householdIds.add(v.getHouseholdId()));
        livingFeeRepository.findAll().forEach(l -> householdIds.add(l.getHouseholdId()));

        double collected = feePaymentRepository.findAll().stream()
                .filter(p -> "PAID".equalsIgnoreCase(p.getStatus()))
                .mapToDouble(p -> p.getAmount() == null ? 0.0 : p.getAmount())
                .sum();

        double expectedUnpaid = feePaymentRepository.findAll().stream()
                .filter(p -> !"PAID".equalsIgnoreCase(p.getStatus()))
                .mapToDouble(p -> p.getAmount() == null ? 0.0 : p.getAmount())
                .sum();

        List<StatisticResponse.UnpaidRow> unpaidRows = new ArrayList<>();
        for (FeePayment p : feePaymentRepository.findByStatusIgnoreCase("UNPAID")) {
            String feeName = feeTypeRepository.findById(p.getFeeTypeId())
                    .map(FeeType::getName)
                    .orElse("Khoản thu");
            unpaidRows.add(new StatisticResponse.UnpaidRow(
                    p.getHouseholdId(),
                    p.getApartmentCode(),
                    feeName,
                    p.getAmount() == null ? 0.0 : p.getAmount()
            ));
        }

        long residentsEstimate = livingFeeRepository.count();

        return new StatisticResponse(
                householdIds.size(),
                residentsEstimate,
                collected,
                expectedUnpaid,
                unpaidRows
        );
    }
}
