package com.bluemoon.apartment.repository;

import com.bluemoon.apartment.entity.ApartmentArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentAreaRepository
        extends JpaRepository<ApartmentArea, Long> {

    Optional<ApartmentArea> findByApartmentNumber(String apartmentNumber);
}