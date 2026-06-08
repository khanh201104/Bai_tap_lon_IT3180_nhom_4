package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.dto.request.ResidentRequest;
import com.bluemoon.apartment.dto.response.ResidentResponse;
import com.bluemoon.apartment.repository.ResidentRepository;
import com.bluemoon.apartment.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Controller
@RequestMapping("/residents")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;
    private final ResidentRepository residentRepository;

    @GetMapping
    public String listResidents(@RequestParam(value = "keyword", required = false) String keyword,
                                Model model) {
        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("residents", residentService.searchResidents(keyword));
        } else {
            model.addAttribute("residents", residentService.getAllResidents());
        }

        model.addAttribute("resident", new ResidentRequest());

        return "resident/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("resident", new ResidentRequest());
        return "resident/create";
    }

    @PostMapping
    public String saveResident(@Valid @ModelAttribute("resident") ResidentRequest residentRequest,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("residents", residentService.getAllResidents());
            model.addAttribute("resident", residentRequest);
            model.addAttribute("modalOpen", true);

            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(this::getResidentErrorMessage)
                    .distinct()
                    .toList());

            return "resident/list";
        }

        if (residentRepository.existsByCitizenId(residentRequest.getCitizenId())) {
            model.addAttribute("residents", residentService.getAllResidents());
            model.addAttribute("resident", residentRequest);
            model.addAttribute("modalOpen", true);
            model.addAttribute("modalErrorMessage", "Số CCCD đã tồn tại. Vui lòng nhập CCCD khác.");

            return "resident/list";
        }

        residentService.saveResident(residentRequest);

        return "redirect:/residents";
    }

    private String getResidentErrorMessage(FieldError error) {
        if ("dateOfBirth".equals(error.getField())) {
            return "Ngày sinh không hợp lệ. Vui lòng nhập theo định dạng dd/MM/yyyy.";
        }

        return error.getDefaultMessage();
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("resident", residentService.getResidentById(id));
        return "resident/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ResidentResponse residentResponse = residentService.getResidentById(id);

        ResidentRequest residentRequest = new ResidentRequest();
        residentRequest.setFullName(residentResponse.getFullName());
        residentRequest.setCitizenId(residentResponse.getCitizenId());
        residentRequest.setPhoneNumber(residentResponse.getPhoneNumber());
        residentRequest.setGender(residentResponse.getGender());
        residentRequest.setDateOfBirth(residentResponse.getDateOfBirth());
        residentRequest.setStatus(
                residentResponse.getStatus() != null
                        ? residentResponse.getStatus().name()
                        : null
        );

        model.addAttribute("resident", residentRequest);
        model.addAttribute("residentId", id);

        return "resident/update";
    }

    @PostMapping("/{id}/update")
    public String updateResident(@PathVariable Long id,
                                 @Valid @ModelAttribute("resident") ResidentRequest residentRequest,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("residentId", id);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(this::getResidentErrorMessage)
                    .distinct()
                    .toList());

            return "resident/update";
        }

        residentService.updateResident(id, residentRequest);

        return "redirect:/residents";
    }

    @PostMapping("/{id}/delete")
    public String deleteResident(@PathVariable Long id) {
        residentService.deleteResident(id);
        return "redirect:/residents";
    }

}
