package com.bluemoon.apartment.scheduler;

import com.bluemoon.apartment.service.FeePaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FeePaymentScheduler {

    private final FeePaymentService feePaymentService;

    @Scheduled(cron = "0 5 0 1 * *", zone = "Asia/Ho_Chi_Minh")
    public void generateMonthlyPaymentsAutomatically() {
        try {
            feePaymentService.generateMonthlyPayments();
            log.info("Đã tự động sinh phiếu thu tháng.");
        } catch (Exception e) {
            log.error("Lỗi khi tự động sinh phiếu thu tháng: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 10 0 1 1 *", zone = "Asia/Ho_Chi_Minh")
    public void generateYearlyPaymentsAutomatically() {
        try {
            feePaymentService.generateYearlyPayments();
            log.info("Đã tự động sinh phiếu thu năm.");
        } catch (Exception e) {
            log.error("Lỗi khi tự động sinh phiếu thu năm: {}", e.getMessage(), e);
        }
    }
}