package com.bluemoon.apartment.service;

import com.bluemoon.apartment.entity.FeePayment;

import java.util.List;
import java.util.Optional;

public interface FeePaymentService {
    List<FeePayment> findAll();

    Optional<FeePayment> findById(Long id);

    FeePayment save(FeePayment payment);

    void deleteById(Long id);

    List<FeePayment> findUnpaid();

    double sumCollected();
}
