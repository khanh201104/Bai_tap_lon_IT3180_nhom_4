package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.repository.FeePaymentRepository;
import com.bluemoon.apartment.service.FeePaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;

    public FeePaymentServiceImpl(FeePaymentRepository feePaymentRepository) {
        this.feePaymentRepository = feePaymentRepository;
    }

    @Override
    public List<FeePayment> findAll() {
        return feePaymentRepository.findAll();
    }

    @Override
    public Optional<FeePayment> findById(Long id) {
        return feePaymentRepository.findById(id);
    }

    @Override
    public FeePayment save(FeePayment payment) {
        return feePaymentRepository.save(payment);
    }

    @Override
    public void deleteById(Long id) {
        feePaymentRepository.deleteById(id);
    }

    @Override
    public List<FeePayment> findUnpaid() {
        return feePaymentRepository.findByStatusIgnoreCase("UNPAID");
    }

    @Override
    public double sumCollected() {
        return feePaymentRepository.findAll().stream()
                .filter(p -> "PAID".equalsIgnoreCase(p.getStatus()))
                .mapToDouble(p -> p.getAmount() == null ? 0.0 : p.getAmount())
                .sum();
    }
}
