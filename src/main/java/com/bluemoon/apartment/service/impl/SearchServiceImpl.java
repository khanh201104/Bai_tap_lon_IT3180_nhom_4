package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.entity.LivingFee;
import com.bluemoon.apartment.entity.Vehicle;
import com.bluemoon.apartment.repository.FeePaymentRepository;
import com.bluemoon.apartment.repository.FeeTypeRepository;
import com.bluemoon.apartment.repository.LivingFeeRepository;
import com.bluemoon.apartment.repository.VehicleRepository;
import com.bluemoon.apartment.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final FeeTypeRepository feeTypeRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final LivingFeeRepository livingFeeRepository;
    private final VehicleRepository vehicleRepository;

    public SearchServiceImpl(
            FeeTypeRepository feeTypeRepository,
            FeePaymentRepository feePaymentRepository,
            LivingFeeRepository livingFeeRepository,
            VehicleRepository vehicleRepository
    ) {
        this.feeTypeRepository = feeTypeRepository;
        this.feePaymentRepository = feePaymentRepository;
        this.livingFeeRepository = livingFeeRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public SearchBundle search(String query) {
        String q = query == null ? "" : query.trim();
        if (q.isEmpty()) {
            return new SearchBundle("", List.of(), List.of(), List.of(), List.of());
        }

        List<FeeType> feeTypes = feeTypeRepository.findByNameContainingIgnoreCase(q);
        List<LivingFee> livingFees = livingFeeRepository.findByNameContainingIgnoreCase(q);
        livingFees.addAll(livingFeeRepository.findByApartmentCodeContainingIgnoreCase(q));

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.addAll(vehicleRepository.findByPlateContainingIgnoreCase(q));
        vehicles.addAll(vehicleRepository.findByApartmentCodeContainingIgnoreCase(q));

        List<FeePayment> payments = new ArrayList<>();
        payments.addAll(feePaymentRepository.findByApartmentCodeContainingIgnoreCase(q));
        feePaymentRepository.findAll().stream()
                .filter(p -> p.getPayerName() != null && p.getPayerName().toLowerCase().contains(q.toLowerCase()))
                .forEach(payments::add);

        return new SearchBundle(q, feeTypes, payments, livingFees, vehicles);
    }
}
