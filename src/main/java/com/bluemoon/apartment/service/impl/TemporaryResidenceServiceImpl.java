package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.dto.request.TemporaryResidenceRequest;
import com.bluemoon.apartment.dto.response.TemporaryResidenceResponse;
import com.bluemoon.apartment.entity.Resident;
import com.bluemoon.apartment.entity.TemporaryResidence;
import com.bluemoon.apartment.mapper.TemporaryResidenceMapper;
import com.bluemoon.apartment.repository.ResidentRepository;
import com.bluemoon.apartment.repository.TemporaryResidenceRepository;
import com.bluemoon.apartment.service.TemporaryResidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemporaryResidenceServiceImpl implements TemporaryResidenceService {

    private final TemporaryResidenceRepository temporaryResidenceRepository;
    private final ResidentRepository residentRepository;
    private final TemporaryResidenceMapper temporaryResidenceMapper;

    @Override
    public List<TemporaryResidenceResponse> getAll() {
        return temporaryResidenceRepository.findAll()
                .stream()
                .map(temporaryResidenceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TemporaryResidenceResponse getById(Long id) {
        TemporaryResidence entity = temporaryResidenceRepository.findById(id).orElse(null);
        return temporaryResidenceMapper.toResponse(entity);
    }

    @Override
    public TemporaryResidenceResponse save(TemporaryResidenceRequest request) {
        Resident resident = residentRepository.findById(request.getResidentId()).orElse(null);

        if (resident != null) {
            TemporaryResidence entity = temporaryResidenceMapper.toEntity(request);
            entity.setResident(resident);

            TemporaryResidence savedEntity = temporaryResidenceRepository.save(entity);
            return temporaryResidenceMapper.toResponse(savedEntity);
        }

        return null;
    }

    @Override
    public TemporaryResidenceResponse update(Long id, TemporaryResidenceRequest request) {
        TemporaryResidence entity = temporaryResidenceRepository.findById(id).orElse(null);

        if (entity != null) {
            Resident resident = residentRepository.findById(request.getResidentId()).orElse(null);

            if (resident != null) {
                temporaryResidenceMapper.updateEntity(entity, request);
                entity.setResident(resident);

                TemporaryResidence updatedEntity = temporaryResidenceRepository.save(entity);
                return temporaryResidenceMapper.toResponse(updatedEntity);
            }
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        temporaryResidenceRepository.deleteById(id);
    }

    @Override
    public List<TemporaryResidenceResponse> searchByResidentName(String name) {
        return temporaryResidenceRepository.findByResidentFullNameContainingIgnoreCase(name)
                .stream()
                .map(temporaryResidenceMapper::toResponse)
                .collect(Collectors.toList());
    }
}