package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.constant.FeeCalculationType;
import com.bluemoon.apartment.constant.FeeCycleType;
import com.bluemoon.apartment.constant.FeeGroup;
import com.bluemoon.apartment.constant.PaymentStatus;
import com.bluemoon.apartment.dto.request.FeePaymentRequest;
import com.bluemoon.apartment.dto.response.FeePaymentResponse;
import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.entity.Household;
import com.bluemoon.apartment.mapper.FeePaymentMapper;
import com.bluemoon.apartment.repository.FeePaymentRepository;
import com.bluemoon.apartment.repository.FeeTypeRepository;
import com.bluemoon.apartment.repository.HouseholdRepository;
import com.bluemoon.apartment.service.FeePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;
    private final FeeTypeRepository feeTypeRepository;
    private final HouseholdRepository householdRepository;
    private final FeePaymentMapper feePaymentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentResponse> getAll() {
        return feePaymentRepository.findAll()
                .stream()
                .map(feePaymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentResponse> search(String keyword) {
        return feePaymentRepository
                .findByHousehold_HouseholdCodeContainingIgnoreCaseOrHousehold_ApartmentNumberContainingIgnoreCaseOrFeeType_NameContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                )
                .stream()
                .map(feePaymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FeePaymentResponse getById(Long id) {
        FeePayment feePayment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu thu"));

        return feePaymentMapper.toResponse(feePayment);
    }

    @Override
    @Transactional
    public void save(FeePaymentRequest request) {
        Household household = householdRepository.findById(request.getHouseholdId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        FeeType feeType = feeTypeRepository.findById(request.getFeeTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phí"));

        if (feePaymentRepository.existsByHousehold_IdAndFeeType_IdAndPeriod(
                household.getId(),
                feeType.getId(),
                request.getPeriod()
        )) {
            throw new IllegalArgumentException("Hộ khẩu này đã có phiếu thu cùng loại phí trong kỳ này");
        }

        BigDecimal amount = calculateAmount(household, feeType, request.getAmount());

        FeePayment feePayment = FeePayment.builder()
                .household(household)
                .feeType(feeType)
                .period(request.getPeriod().trim())
                .amount(amount)
                .dueDate(request.getDueDate())
                .paidDate(null)
                .status(PaymentStatus.UNPAID)
                .note(request.getNote())
                .build();

        feePaymentRepository.save(feePayment);
    }

    @Override
    @Transactional
    public void update(Long id, FeePaymentRequest request) {
        FeePayment feePayment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu thu"));

        Household household = householdRepository.findById(request.getHouseholdId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hộ khẩu"));

        FeeType feeType = feeTypeRepository.findById(request.getFeeTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phí"));

        boolean duplicated = feePaymentRepository.existsByHousehold_IdAndFeeType_IdAndPeriod(
                household.getId(),
                feeType.getId(),
                request.getPeriod()
        );

        boolean sameRecord = feePayment.getHousehold().getId().equals(household.getId())
                && feePayment.getFeeType().getId().equals(feeType.getId())
                && feePayment.getPeriod().equals(request.getPeriod());

        if (duplicated && !sameRecord) {
            throw new IllegalArgumentException("Hộ khẩu này đã có phiếu thu cùng loại phí trong kỳ này");
        }

        BigDecimal amount = calculateAmount(household, feeType, request.getAmount());

        feePayment.setHousehold(household);
        feePayment.setFeeType(feeType);
        feePayment.setPeriod(request.getPeriod().trim());
        feePayment.setAmount(amount);
        feePayment.setDueDate(request.getDueDate());
        feePayment.setNote(request.getNote());

        feePaymentRepository.save(feePayment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FeePayment feePayment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu thu"));

        feePaymentRepository.delete(feePayment);
    }

    @Override
    @Transactional
    public void markAsPaid(Long id) {
        FeePayment feePayment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu thu"));

        if (feePayment.getStatus() == PaymentStatus.CANCELLED) {
            throw new IllegalArgumentException("Không thể thanh toán phiếu thu đã hủy");
        }

        feePayment.setStatus(PaymentStatus.PAID);
        feePayment.setPaidDate(LocalDate.now());

        feePaymentRepository.save(feePayment);
    }

    @Override
    @Transactional
    public void cancelPayment(Long id) {
        FeePayment feePayment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu thu"));

        feePayment.setStatus(PaymentStatus.CANCELLED);
        feePayment.setPaidDate(null);

        feePaymentRepository.save(feePayment);
    }

    @Override
    @Transactional
    public void generateMonthlyPayments() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        String period = currentMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        LocalDate dueDate = currentMonth.atEndOfMonth();

        List<FeeType> monthlyFeeTypes = feeTypeRepository.findByFeeGroupAndActiveTrue(FeeGroup.ANNUAL)
                .stream()
                .filter(feeType -> feeType.getCycleType() == FeeCycleType.MONTHLY)
                .toList();

        List<Household> households = householdRepository.findAll();

        generatePeriodicPayments(monthlyFeeTypes, households, period, dueDate);
    }

    @Override
    @Transactional
    public void generateYearlyPayments() {
        LocalDate now = LocalDate.now();

        String period = String.valueOf(now.getYear());
        LocalDate dueDate = LocalDate.of(now.getYear(), 12, 31);

        List<FeeType> yearlyFeeTypes = feeTypeRepository.findByFeeGroupAndActiveTrue(FeeGroup.ANNUAL)
                .stream()
                .filter(feeType -> feeType.getCycleType() == FeeCycleType.YEARLY)
                .toList();

        List<Household> households = householdRepository.findAll();

        generatePeriodicPayments(yearlyFeeTypes, households, period, dueDate);
    }

    private void generatePeriodicPayments(List<FeeType> feeTypes,
                                          List<Household> households,
                                          String period,
                                          LocalDate dueDate) {
        for (FeeType feeType : feeTypes) {
            for (Household household : households) {
                boolean existed = feePaymentRepository.existsByHousehold_IdAndFeeType_IdAndPeriod(
                        household.getId(),
                        feeType.getId(),
                        period
                );

                if (existed) {
                    continue;
                }

                BigDecimal amount = calculateAmount(household, feeType, feeType.getUnitPrice());

                FeePayment feePayment = FeePayment.builder()
                        .household(household)
                        .feeType(feeType)
                        .period(period)
                        .amount(amount)
                        .dueDate(dueDate)
                        .paidDate(null)
                        .status(PaymentStatus.UNPAID)
                        .note("Phiếu thu được hệ thống tự sinh")
                        .build();

                feePaymentRepository.save(feePayment);
            }
        }
    }

    private BigDecimal calculateAmount(Household household,
                                       FeeType feeType,
                                       BigDecimal manualAmount) {
        if (feeType.getCalculationType() == FeeCalculationType.FIXED) {
            return feeType.getUnitPrice();
        }

        if (feeType.getCalculationType() == FeeCalculationType.BY_AREA) {
            if (household.getApartmentArea() == null || household.getApartmentArea() <= 0) {
                throw new IllegalArgumentException("Hộ khẩu chưa có diện tích căn hộ hợp lệ");
            }

            return feeType.getUnitPrice()
                    .multiply(BigDecimal.valueOf(household.getApartmentArea()))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        if (feeType.getCalculationType() == FeeCalculationType.MANUAL) {
            if (manualAmount == null || manualAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Số tiền phải lớn hơn 0");
            }

            return manualAmount;
        }

        throw new IllegalArgumentException("Cách tính phí không hợp lệ");
    }
}