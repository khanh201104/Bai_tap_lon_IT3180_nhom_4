package com.bluemoon.apartment.service;

import com.bluemoon.apartment.entity.ApartmentArea;

import java.util.List;

public interface ApartmentAreaService {

    List<ApartmentArea> getAll();

    ApartmentArea getById(Long id);

    ApartmentArea save(ApartmentArea area);

    void delete(Long id);
}