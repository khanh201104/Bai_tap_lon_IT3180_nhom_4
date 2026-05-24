package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.dto.request.ResidentRequest;
import com.bluemoon.apartment.dto.response.ResidentResponse;
import com.bluemoon.apartment.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/residents")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @GetMapping
    public String listResidents(Model model, @RequestParam(required = false) String keyword) {
        List<ResidentResponse> residents;
        if (keyword != null && !keyword.isEmpty()) {
            residents = residentService.searchResidents(keyword);
        } else {
            residents = residentService.getAllResidents();
        }
        model.addAttribute("residents", residents);
        return "resident/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("resident", new ResidentRequest());
        return "resident/create";
    }

    @PostMapping
    public String saveResident(@ModelAttribute ResidentRequest residentRequest) {
        residentService.saveResident(residentRequest);
        return "redirect:/residents";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("resident", residentService.getResidentById(id));
        return "resident/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("resident", residentService.getResidentById(id));
        return "resident/update";
    }

    @PostMapping("/{id}/update")
    public String updateResident(@PathVariable Long id, @ModelAttribute ResidentRequest residentRequest) {
        residentService.updateResident(id, residentRequest);
        return "redirect:/residents";
    }

    @PostMapping("/{id}/delete")
    public String deleteResident(@PathVariable Long id) {
        residentService.deleteResident(id);
        return "redirect:/residents";
    }
}
