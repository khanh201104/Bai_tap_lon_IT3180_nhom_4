package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.entity.FeeType;
import com.bluemoon.apartment.service.FeeTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fees")
public class FeeTypeController {

    private final FeeTypeService feeTypeService;

    public FeeTypeController(FeeTypeService feeTypeService) {
        this.feeTypeService = feeTypeService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("fees", q == null || q.isBlank()
                ? feeTypeService.findAll()
                : feeTypeService.searchByName(q));
        model.addAttribute("q", q == null ? "" : q);
        return "fee/fee-type-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("fee", new FeeType());
        return "fee/fee-type-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute FeeType fee, RedirectAttributes ra) {
        feeTypeService.save(fee);
        ra.addFlashAttribute("message", "Đã thêm loại phí");
        return "redirect:/fees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        FeeType fee = feeTypeService.findById(id).orElseThrow();
        model.addAttribute("fee", fee);
        return "fee/fee-type-update";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute FeeType fee, RedirectAttributes ra) {
        fee.setId(id);
        feeTypeService.save(fee);
        ra.addFlashAttribute("message", "Đã cập nhật loại phí");
        return "redirect:/fees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        feeTypeService.deleteById(id);
        ra.addFlashAttribute("message", "Đã xóa loại phí");
        return "redirect:/fees";
    }
}
