package com.bluemoon.apartment.service.impl;

import com.bluemoon.apartment.entity.ApartmentArea;
import com.bluemoon.apartment.repository.ApartmentAreaRepository;
import com.bluemoon.apartment.service.ApartmentAreaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentAreaServiceImpl implements ApartmentAreaService {

    private final ApartmentAreaRepository apartmentAreaRepository;

    public ApartmentAreaServiceImpl(ApartmentAreaRepository apartmentAreaRepository) {
        this.apartmentAreaRepository = apartmentAreaRepository;
    }

    @Override
    public List<ApartmentArea> getAll() {
        return apartmentAreaRepository.findAll();
    }

    @Override
    public ApartmentArea getById(Long id) {
        return apartmentAreaRepository.findById(id).orElse(null);
    }

    @Override
    public ApartmentArea save(ApartmentArea area) {
        return apartmentAreaRepository.save(area);
    }

    @Override
    public void delete(Long id) {
        apartmentAreaRepository.deleteById(id);
    }
}