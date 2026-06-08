package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.dto.request.HouseholdMemberRequest;
import com.bluemoon.apartment.dto.request.HouseholdRequest;
import com.bluemoon.apartment.dto.response.HouseholdResponse;
import com.bluemoon.apartment.service.HouseholdMemberService;
import com.bluemoon.apartment.service.HouseholdService;
import com.bluemoon.apartment.service.ResidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/households")
@RequiredArgsConstructor
public class HouseholdController {

    private final HouseholdService householdService;
    private final HouseholdMemberService householdMemberService;
    private final ResidentService residentService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) String keyword) {
        loadHouseholdList(model, keyword);

        model.addAttribute("household", new HouseholdRequest());
        model.addAttribute("memberRequest", new HouseholdMemberRequest());

        return "household/list";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("household") HouseholdRequest request,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            loadHouseholdList(model, null);
            model.addAttribute("memberRequest", new HouseholdMemberRequest());
            model.addAttribute("createModalOpen", true);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .toList());

            return "household/list";
        }

        try {
            householdService.save(request);
        } catch (IllegalArgumentException e) {
            loadHouseholdList(model, null);
            model.addAttribute("memberRequest", new HouseholdMemberRequest());
            model.addAttribute("createModalOpen", true);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "household/list";
        }

        return "redirect:/households";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("household") HouseholdRequest request,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            loadHouseholdList(model, null);
            model.addAttribute("memberRequest", new HouseholdMemberRequest());
            model.addAttribute("editErrorId", id);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .toList());

            return "household/list";
        }

        try {
            householdService.update(id, request);
        } catch (IllegalArgumentException e) {
            loadHouseholdList(model, null);
            model.addAttribute("memberRequest", new HouseholdMemberRequest());
            model.addAttribute("editErrorId", id);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "household/list";
        }

        return "redirect:/households";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        householdService.delete(id);

        return "redirect:/households";
    }

    @PostMapping("/{householdId}/members")
    public String addMember(@PathVariable Long householdId,
                            @Valid @ModelAttribute("memberRequest") HouseholdMemberRequest request,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            loadHouseholdList(model, null);
            model.addAttribute("household", new HouseholdRequest());
            model.addAttribute("memberErrorHouseholdId", householdId);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .toList());

            return "household/list";
        }

        try {
            householdMemberService.addMember(householdId, request);
        } catch (IllegalArgumentException e) {
            loadHouseholdList(model, null);
            model.addAttribute("household", new HouseholdRequest());
            model.addAttribute("memberErrorHouseholdId", householdId);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "household/list";
        }

        return "redirect:/households";
    }

    @PostMapping("/{householdId}/members/{memberId}/delete")
    public String removeMember(@PathVariable Long householdId,
                               @PathVariable Long memberId,
                               Model model) {
        try {
            householdMemberService.removeMember(householdId, memberId);
        } catch (IllegalArgumentException e) {
            loadHouseholdList(model, null);
            model.addAttribute("household", new HouseholdRequest());
            model.addAttribute("memberRequest", new HouseholdMemberRequest());
            model.addAttribute("memberErrorHouseholdId", householdId);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "household/list";
        }

        return "redirect:/households";
    }

    private void loadHouseholdList(Model model, String keyword) {
        List<HouseholdResponse> households;

        if (keyword != null && !keyword.trim().isEmpty()) {
            households = householdService.search(keyword.trim());
        } else {
            households = householdService.getAll();
        }

        model.addAttribute("households", households);
        model.addAttribute("residents", residentService.getAllResidents());
        model.addAttribute("keyword", keyword);
    }
}