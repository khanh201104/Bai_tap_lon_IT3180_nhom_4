package com.bluemoon.apartment.service;

import com.bluemoon.apartment.dto.request.FeePaymentRequest;
import com.bluemoon.apartment.dto.response.FeePaymentResponse;

import java.util.List;

public interface FeePaymentService {

    List<FeePaymentResponse> getAll();

    List<FeePaymentResponse> search(String keyword);

    FeePaymentResponse getById(Long id);

    void save(FeePaymentRequest request);

    void update(Long id, FeePaymentRequest request);

    void delete(Long id);

    void markAsPaid(Long id);

    void cancelPayment(Long id);

    void generateMonthlyPayments();

    void generateYearlyPayments();
}