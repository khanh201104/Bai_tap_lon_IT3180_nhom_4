package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.constant.FeeCycleType;
import com.bluemoon.apartment.constant.FeeGroup;
import com.bluemoon.apartment.dto.request.FeeTypeRequest;
import com.bluemoon.apartment.dto.response.FeeTypeResponse;
import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.mapper.FeeTypeMapper;
import com.bluemoon.apartment.repository.FeePaymentRepository;
import com.bluemoon.apartment.repository.FeeTypeRepository;
import com.bluemoon.apartment.service.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeTypeServiceImpl implements FeeTypeService {

    private final FeeTypeRepository feeTypeRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final FeeTypeMapper feeTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FeeTypeResponse> getAll() {
        return feeTypeRepository.findAll()
                .stream()
                .map(feeTypeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeTypeResponse> getActiveFeeTypes() {
        return feeTypeRepository.findByActiveTrue()
                .stream()
                .map(feeTypeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeTypeResponse> search(String keyword) {
        return feeTypeRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(feeTypeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FeeTypeResponse getById(Long id) {
        FeeType feeType = feeTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phí"));

        return feeTypeMapper.toResponse(feeType);
    }

    @Override
    @Transactional
    public void save(FeeTypeRequest request) {
        validateFeeTypeRule(request);

        if (feeTypeRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tên loại phí đã tồn tại");
        }

        FeeType feeType = FeeType.builder()
                .name(request.getName().trim())
                .description(request.getDescription())
                .feeGroup(request.getFeeGroup())
                .cycleType(request.getCycleType())
                .calculationType(request.getCalculationType())
                .unitPrice(request.getUnitPrice())
                .active(request.getActive())
                .build();

        feeTypeRepository.save(feeType);
    }

    @Override
    @Transactional
    public void update(Long id, FeeTypeRequest request) {
        validateFeeTypeRule(request);

        FeeType feeType = feeTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phí"));

        if (feeTypeRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new IllegalArgumentException("Tên loại phí đã tồn tại");
        }

        feeType.setName(request.getName().trim());
        feeType.setDescription(request.getDescription());
        feeType.setFeeGroup(request.getFeeGroup());
        feeType.setCycleType(request.getCycleType());
        feeType.setCalculationType(request.getCalculationType());
        feeType.setUnitPrice(request.getUnitPrice());
        feeType.setActive(request.getActive());

        feeTypeRepository.save(feeType);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FeeType feeType = feeTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phí"));

        if (!feePaymentRepository.findByFeeType_Id(id).isEmpty()) {
            throw new IllegalArgumentException("Không thể xóa loại phí đã phát sinh phiếu thu");
        }

        feeTypeRepository.delete(feeType);
    }

    private void validateFeeTypeRule(FeeTypeRequest request) {
        if (request.getFeeGroup() == FeeGroup.ANNUAL
                && request.getCycleType() == FeeCycleType.ONE_TIME) {
            throw new IllegalArgumentException("Phí thường niên phải chọn chu kỳ theo tháng hoặc theo năm");
        }

        if (request.getFeeGroup() == FeeGroup.ARISING
                && request.getCycleType() != FeeCycleType.ONE_TIME) {
            throw new IllegalArgumentException("Phí phát sinh phải có chu kỳ không lặp");
        }
    }
}