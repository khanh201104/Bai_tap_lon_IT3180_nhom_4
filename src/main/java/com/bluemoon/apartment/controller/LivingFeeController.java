package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.entity.LivingFee;
import com.bluemoon.apartment.service.LivingFeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/living-fees")
public class LivingFeeController {

    private final LivingFeeService livingFeeService;

    public LivingFeeController(LivingFeeService livingFeeService) {
        this.livingFeeService = livingFeeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", livingFeeService.findAll());
        return "fee/living-fee-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("item", new LivingFee());
        return "fee/living-fee-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute LivingFee item, RedirectAttributes ra) {
        livingFeeService.save(item);
        ra.addFlashAttribute("message", "Đã thêm phí sinh hoạt");
        return "redirect:/living-fees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", livingFeeService.findById(id).orElseThrow());
        return "fee/living-fee-update";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute LivingFee item, RedirectAttributes ra) {
        item.setId(id);
        livingFeeService.save(item);
        ra.addFlashAttribute("message", "Đã cập nhật phí sinh hoạt");
        return "redirect:/living-fees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        livingFeeService.deleteById(id);
        ra.addFlashAttribute("message", "Đã xóa phí sinh hoạt");
        return "redirect:/living-fees";
    }
}
