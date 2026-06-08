package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.dto.request.TemporaryResidenceRequest;
import com.bluemoon.apartment.dto.response.TemporaryResidenceResponse;
import com.bluemoon.apartment.service.ResidentService;
import com.bluemoon.apartment.service.TemporaryResidenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/temporary")
@RequiredArgsConstructor
public class TemporaryResidenceController {

    private final TemporaryResidenceService temporaryResidenceService;
    private final ResidentService residentService;

    @GetMapping
    public String list(Model model, @RequestParam(required = false) String keyword) {
        List<TemporaryResidenceResponse> temporaries;

        if (keyword != null && !keyword.trim().isEmpty()) {
            temporaries = temporaryResidenceService.searchByResidentName(keyword.trim());
        } else {
            temporaries = temporaryResidenceService.getAll();
        }

        model.addAttribute("temporaries", temporaries);
        model.addAttribute("residents", residentService.getAllResidents());

        return "temporary/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("temporary", new TemporaryResidenceRequest());
        model.addAttribute("residents", residentService.getAllResidents());

        return "temporary/create";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("temporary") TemporaryResidenceRequest request,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("residents", residentService.getAllResidents());
            return "temporary/create";
        }

        temporaryResidenceService.save(request);

        return "redirect:/temporary";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("temporary", temporaryResidenceService.getById(id));

        return "temporary/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        TemporaryResidenceResponse response = temporaryResidenceService.getById(id);

        TemporaryResidenceRequest request = new TemporaryResidenceRequest();

        if (response != null) {
            if (response.getResident() != null) {
                request.setResidentId(response.getResident().getId());
            }

            request.setType(response.getType());
            request.setStartDate(response.getStartDate());
            request.setEndDate(response.getEndDate());
            request.setReason(response.getReason());
        }

        model.addAttribute("temporary", request);
        model.addAttribute("id", id);
        model.addAttribute("residents", residentService.getAllResidents());

        return "temporary/update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("temporary") TemporaryResidenceRequest request,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            List<TemporaryResidenceResponse> temporaries = temporaryResidenceService.getAll();

            model.addAttribute("temporaries", temporaries);
            model.addAttribute("residents", residentService.getAllResidents());
            model.addAttribute("editErrorId", id);

            return "temporary/list";
        }

        temporaryResidenceService.update(id, request);

        return "redirect:/temporary";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        temporaryResidenceService.delete(id);

        return "redirect:/temporary";
    }
}