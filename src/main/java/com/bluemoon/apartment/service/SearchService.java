package com.bluemoon.apartment.service;

import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.entity.LivingFee;
import com.bluemoon.apartment.entity.Vehicle;

import java.util.List;

public interface SearchService {
    record SearchBundle(
            String query,
            List<FeeType> feeTypes,
            List<FeePayment> feePayments,
            List<LivingFee> livingFees,
            List<Vehicle> vehicles
    ) {}

    SearchBundle search(String query);
}
