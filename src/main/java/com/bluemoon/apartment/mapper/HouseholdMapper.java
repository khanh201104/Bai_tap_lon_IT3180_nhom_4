package com.bluemoon.apartment.map;

import com.bluemoon.apartment.dto.response.HouseholdResponse;
import com.bluemoon.apartment.entity.Household;

public class HouseholdMapper {

    public static HouseholdResponse toResponse(Household household) {

        if (household == null) {
            return null;
        }

        HouseholdResponse response = new HouseholdResponse();

        response.setId(household.getId());
        response.setHouseholdCode(household.getHouseholdCode());
        response.setHeadName(household.getHeadName());
        response.setApartmentNumber(household.getApartmentNumber());

        return response;
    }

    public static Household toEntity(HouseholdResponse response) {

        if (response == null) {
            return null;
        }

        Household household = new Household();

        household.setId(response.getId());
        household.setHouseholdCode(response.getHouseholdCode());
        household.setHeadName(response.getHeadName());
        household.setApartmentNumber(response.getApartmentNumber());

        return household;
    }
}