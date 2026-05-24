package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.entity.FeePayment;
import com.bluemoon.apartment.service.FeePaymentService;
import com.bluemoon.apartment.service.FeeTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fee-payments")
public class FeePaymentController {

    private final FeePaymentService feePaymentService;
    private final FeeTypeService feeTypeService;

    public FeePaymentController(FeePaymentService feePaymentService, FeeTypeService feeTypeService) {
        this.feePaymentService = feePaymentService;
        this.feeTypeService = feeTypeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("payments", feePaymentService.findAll());
        model.addAttribute("totalCollected", feePaymentService.sumCollected());
        model.addAttribute("unpaid", feePaymentService.findUnpaid());
        return "fee/payment-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("payment", new FeePayment());
        model.addAttribute("feeTypes", feeTypeService.findAll());
        return "fee/payment-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute FeePayment payment, RedirectAttributes ra) {
        if ("PAID".equalsIgnoreCase(payment.getStatus()) && payment.getPaymentDate() == null) {
            payment.setPaymentDate(java.time.LocalDate.now());
        }
        feePaymentService.save(payment);
        ra.addFlashAttribute("message", "Đã ghi nhận đóng phí");
        return "redirect:/fee-payments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        FeePayment payment = feePaymentService.findById(id).orElseThrow();
        model.addAttribute("payment", payment);
        model.addAttribute("feeTypes", feeTypeService.findAll());
        return "fee/payment-update";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute FeePayment payment, RedirectAttributes ra) {
        payment.setId(id);
        if ("PAID".equalsIgnoreCase(payment.getStatus()) && payment.getPaymentDate() == null) {
            payment.setPaymentDate(java.time.LocalDate.now());
        }
        feePaymentService.save(payment);
        ra.addFlashAttribute("message", "Đã cập nhật trạng thái đóng phí");
        return "redirect:/fee-payments";
    }
}
