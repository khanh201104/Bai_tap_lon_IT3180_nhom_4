package com.bluemoon.apartment.map;

import com.bluemoon.apartment.dto.response.ApartmentAreaResponse;
import com.bluemoon.apartment.entity.ApartmentArea;

public class ApartmentAreaMapper {

    public static ApartmentAreaResponse toResponse(ApartmentArea area) {

        if (area == null) {
            return null;
        }

        ApartmentAreaResponse response = new ApartmentAreaResponse();

        response.setId(area.getId());
        response.setApartmentNumber(area.getApartmentNumber());
        response.setArea(area.getArea());
        response.setServiceFee(area.getServiceFee());

        return response;
    }

    public static ApartmentArea toEntity(ApartmentAreaResponse response) {

        if (response == null) {
            return null;
        }

        ApartmentArea area = new ApartmentArea();

        area.setId(response.getId());
        area.setApartmentNumber(response.getApartmentNumber());
        area.setArea(response.getArea());
        area.setServiceFee(response.getServiceFee());

        return area;
    }
}