package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.constant.VehicleStatus;
import com.bluemoon.apartment.constant.VehicleType;
import com.bluemoon.apartment.dto.request.VehicleRequest;
import com.bluemoon.apartment.service.HouseholdService;
import com.bluemoon.apartment.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final HouseholdService householdService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Long householdId,
                       @RequestParam(required = false) VehicleType vehicleType) {

        loadListData(model, keyword, householdId, vehicleType);
        model.addAttribute("vehicle", new VehicleRequest());

        return "vehicle/list";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("vehicle") VehicleRequest request,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            loadListData(model, null, null, null);
            model.addAttribute("createError", true);
            return "vehicle/list";
        }

        try {
            vehicleService.save(request);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("licensePlate", "error.vehicle", e.getMessage());
            loadListData(model, null, null, null);
            model.addAttribute("createError", true);
            return "vehicle/list";
        }

        return "redirect:/vehicles";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("vehicle") VehicleRequest request,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            loadListData(model, null, null, null);
            model.addAttribute("editErrorId", id);
            return "vehicle/list";
        }

        try {
            vehicleService.update(id, request);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("licensePlate", "error.vehicle", e.getMessage());
            loadListData(model, null, null, null);
            model.addAttribute("editErrorId", id);
            return "vehicle/list";
        }

        return "redirect:/vehicles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return "redirect:/vehicles";
    }

    private void loadListData(Model model, String keyword, Long householdId, VehicleType vehicleType) {
        model.addAttribute("vehicles", vehicleService.search(keyword, householdId, vehicleType));
        model.addAttribute("households", householdService.getAll());
        model.addAttribute("vehicleTypes", VehicleType.values());
        model.addAttribute("vehicleStatuses", VehicleStatus.values());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedHouseholdId", householdId);
        model.addAttribute("selectedVehicleType", vehicleType);
    }
}