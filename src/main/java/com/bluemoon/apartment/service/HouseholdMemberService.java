package com.bluemoon.apartment.service;

import com.bluemoon.apartment.dto.request.HouseholdMemberRequest;

public interface HouseholdMemberService {

    void addMember(Long householdId, HouseholdMemberRequest request);

    void removeMember(Long householdId, Long memberId);
}