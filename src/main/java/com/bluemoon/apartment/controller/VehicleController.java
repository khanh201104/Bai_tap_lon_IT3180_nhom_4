package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.entity.Vehicle;
import com.bluemoon.apartment.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("vehicles", vehicleService.search(q == null ? "" : q));
        model.addAttribute("q", q == null ? "" : q);
        return "vehicle/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "vehicle/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Vehicle vehicle, RedirectAttributes ra) {
        vehicleService.save(vehicle);
        ra.addFlashAttribute("message", "Đã thêm phương tiện");
        return "redirect:/vehicles";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleService.findById(id).orElseThrow());
        return "vehicle/update";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Vehicle vehicle, RedirectAttributes ra) {
        vehicle.setId(id);
        vehicleService.save(vehicle);
        ra.addFlashAttribute("message", "Đã cập nhật phương tiện");
        return "redirect:/vehicles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        vehicleService.deleteById(id);
        ra.addFlashAttribute("message", "Đã xóa phương tiện");
        return "redirect:/vehicles";
    }
}
