package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.constant.PaymentStatus;
import com.bluemoon.apartment.constant.VehicleStatus;
import com.bluemoon.apartment.constant.VehicleType;
import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.entity.Vehicle;
import com.bluemoon.apartment.repository.FeePaymentRepository;
import com.bluemoon.apartment.repository.HouseholdRepository;
import com.bluemoon.apartment.repository.ResidentRepository;
import com.bluemoon.apartment.repository.TemporaryResidenceRepository;
import com.bluemoon.apartment.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final HouseholdRepository householdRepository;
    private final ResidentRepository residentRepository;
    private final TemporaryResidenceRepository temporaryResidenceRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final VehicleRepository vehicleRepository;

    @GetMapping({"/dashboard"})
    public String index(Model model) {
        long householdCount = householdRepository.count();
        long residentCount = residentRepository.count();
        long temporaryCount = temporaryResidenceRepository.count();

        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Vehicle> activeVehicles = vehicles.stream()
                .filter(vehicle -> VehicleStatus.ACTIVE.equals(vehicle.getStatus()))
                .toList();

        long vehicleCount = vehicles.size();
        long activeVehicleCount = activeVehicles.size();

        BigDecimal parkingEstimateAmount = activeVehicles.stream()
                .filter(vehicle -> vehicle.getVehicleType() != null)
                .map(vehicle -> BigDecimal.valueOf(vehicle.getVehicleType().getMonthlyFee()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<FeePayment> feePayments = feePaymentRepository.findAll();

        long paidCount = countPayment(feePayments, PaymentStatus.PAID);
        long unpaidCount = countPayment(feePayments, PaymentStatus.UNPAID);
        long overdueCount = countPayment(feePayments, PaymentStatus.OVERDUE);
        long cancelledCount = countPayment(feePayments, PaymentStatus.CANCELLED);

        BigDecimal paidAmount = sumAmount(feePayments, payment -> PaymentStatus.PAID.equals(payment.getStatus()));
        BigDecimal unpaidAmount = sumAmount(feePayments, payment -> PaymentStatus.UNPAID.equals(payment.getStatus()));
        BigDecimal overdueAmount = sumAmount(feePayments, payment -> PaymentStatus.OVERDUE.equals(payment.getStatus()));

        BigDecimal receivableAmount = paidAmount.add(unpaidAmount).add(overdueAmount);
        int paymentRate = calculatePaymentRate(paidAmount, receivableAmount);

        model.addAttribute("householdCount", householdCount);
        model.addAttribute("residentCount", residentCount);
        model.addAttribute("temporaryCount", temporaryCount);
        model.addAttribute("vehicleCount", vehicleCount);
        model.addAttribute("activeVehicleCount", activeVehicleCount);

        model.addAttribute("paidCount", paidCount);
        model.addAttribute("unpaidCount", unpaidCount);
        model.addAttribute("overdueCount", overdueCount);
        model.addAttribute("cancelledCount", cancelledCount);

        model.addAttribute("paidAmount", paidAmount);
        model.addAttribute("unpaidAmount", unpaidAmount);
        model.addAttribute("overdueAmount", overdueAmount);
        model.addAttribute("parkingEstimateAmount", parkingEstimateAmount);
        model.addAttribute("paymentRate", paymentRate);

        model.addAttribute("paidAmountText", formatCurrency(paidAmount));
        model.addAttribute("unpaidAmountText", formatCurrency(unpaidAmount));
        model.addAttribute("overdueAmountText", formatCurrency(overdueAmount));
        model.addAttribute("parkingEstimateAmountText", formatCurrency(parkingEstimateAmount));

        model.addAttribute("feeStatusLabels", List.of("Đã đóng", "Chưa đóng", "Quá hạn", "Đã hủy"));
        model.addAttribute("feeStatusCounts", List.of(paidCount, unpaidCount, overdueCount, cancelledCount));

        model.addAttribute("vehicleTypeLabels", Arrays.stream(VehicleType.values())
                .map(VehicleType::getDisplayName)
                .toList());

        model.addAttribute("vehicleTypeCounts", Arrays.stream(VehicleType.values())
                .map(type -> activeVehicles.stream()
                        .filter(vehicle -> type.equals(vehicle.getVehicleType()))
                        .count())
                .toList());

        addFeeTrendData(model, feePayments);

        return "dashboard/index";
    }

    private long countPayment(List<FeePayment> payments, PaymentStatus status) {
        return payments.stream()
                .filter(payment -> status.equals(payment.getStatus()))
                .count();
    }

    private BigDecimal sumAmount(List<FeePayment> payments, Predicate<FeePayment> predicate) {
        return payments.stream()
                .filter(predicate)
                .map(payment -> payment.getAmount() == null ? BigDecimal.ZERO : payment.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int calculatePaymentRate(BigDecimal paidAmount, BigDecimal receivableAmount) {
        if (receivableAmount == null || receivableAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        return paidAmount
                .multiply(BigDecimal.valueOf(100))
                .divide(receivableAmount, 0, RoundingMode.HALF_UP)
                .intValue();
    }

    private String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }

    private void addFeeTrendData(Model model, List<FeePayment> feePayments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth currentMonth = YearMonth.now();

        List<String> periodLabels = java.util.stream.IntStream.rangeClosed(0, 5)
                .mapToObj(index -> currentMonth.minusMonths(5 - index).format(formatter))
                .toList();

        List<BigDecimal> periodAmounts = periodLabels.stream()
                .map(period -> feePayments.stream()
                        .filter(payment -> period.equals(payment.getPeriod()))
                        .map(payment -> payment.getAmount() == null ? BigDecimal.ZERO : payment.getAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .toList();

        model.addAttribute("feeTrendLabels", periodLabels);
        model.addAttribute("feeTrendAmounts", periodAmounts);
    }
}