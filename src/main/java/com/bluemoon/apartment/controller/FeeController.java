package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.dto.request.FeePaymentRequest;
import com.bluemoon.apartment.dto.request.FeeTypeRequest;
import com.bluemoon.apartment.dto.response.FeePaymentResponse;
import com.bluemoon.apartment.service.FeePaymentService;
import com.bluemoon.apartment.service.FeeTypeService;
import com.bluemoon.apartment.service.HouseholdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/fees")
@RequiredArgsConstructor
public class FeeController {

    private final FeeTypeService feeTypeService;
    private final FeePaymentService feePaymentService;
    private final HouseholdService householdService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) String keyword) {
        loadFeePage(model, keyword);

        model.addAttribute("feePayment", new FeePaymentRequest());

        return "fee/list";
    }

    @GetMapping("/types")
    public String listFeeTypes(Model model,
                               @RequestParam(required = false) String keyword) {
        loadFeeTypePage(model, keyword);

        model.addAttribute("feeType", new FeeTypeRequest());

        return "fee/type-list";
    }

    @PostMapping("/types")
    public String saveFeeType(@Valid @ModelAttribute("feeType") FeeTypeRequest request,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            loadFeeTypePage(model, null);
            model.addAttribute("feeTypeCreateOpen", true);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .toList());

            return "fee/type-list";
        }

        try {
            feeTypeService.save(request);
        } catch (IllegalArgumentException e) {
            loadFeeTypePage(model, null);
            model.addAttribute("feeTypeCreateOpen", true);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "fee/type-list";
        }

        return "redirect:/fees/types";
    }

    @PostMapping("/types/{id}/update")
    public String updateFeeType(@PathVariable Long id,
                                @Valid @ModelAttribute("feeType") FeeTypeRequest request,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            loadFeeTypePage(model, null);
            model.addAttribute("feeTypeEditErrorId", id);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .toList());

            return "fee/type-list";
        }

        try {
            feeTypeService.update(id, request);
        } catch (IllegalArgumentException e) {
            loadFeeTypePage(model, null);
            model.addAttribute("feeTypeEditErrorId", id);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "fee/type-list";
        }

        return "redirect:/fees/types";
    }

    @PostMapping("/types/{id}/delete")
    public String deleteFeeType(@PathVariable Long id, Model model) {
        try {
            feeTypeService.delete(id);
        } catch (IllegalArgumentException e) {
            loadFeeTypePage(model, null);
            model.addAttribute("feeType", new FeeTypeRequest());
            model.addAttribute("feeTypeDeleteError", e.getMessage());

            return "fee/type-list";
        }

        return "redirect:/fees/types";
    }

    @PostMapping("/payments")
    public String saveFeePayment(@Valid @ModelAttribute("feePayment") FeePaymentRequest request,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            loadFeePage(model, null);
            model.addAttribute("feePaymentCreateOpen", true);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .toList());

            return "fee/list";
        }

        try {
            feePaymentService.save(request);
        } catch (IllegalArgumentException e) {
            loadFeePage(model, null);
            model.addAttribute("feePaymentCreateOpen", true);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "fee/list";
        }

        return "redirect:/fees";
    }

    @PostMapping("/payments/{id}/update")
    public String updateFeePayment(@PathVariable Long id,
                                   @Valid @ModelAttribute("feePayment") FeePaymentRequest request,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            loadFeePage(model, null);
            model.addAttribute("feePaymentEditErrorId", id);
            model.addAttribute("validationErrors", bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .toList());

            return "fee/list";
        }

        try {
            feePaymentService.update(id, request);
        } catch (IllegalArgumentException e) {
            loadFeePage(model, null);
            model.addAttribute("feePaymentEditErrorId", id);
            model.addAttribute("modalErrorMessage", e.getMessage());

            return "fee/list";
        }

        return "redirect:/fees";
    }

    @PostMapping("/payments/{id}/delete")
    public String deleteFeePayment(@PathVariable Long id) {
        feePaymentService.delete(id);

        return "redirect:/fees";
    }

    @PostMapping("/payments/{id}/paid")
    public String markAsPaid(@PathVariable Long id, Model model) {
        try {
            feePaymentService.markAsPaid(id);
        } catch (IllegalArgumentException e) {
            loadFeePage(model, null);
            model.addAttribute("feePayment", new FeePaymentRequest());
            model.addAttribute("feePaymentActionError", e.getMessage());

            return "fee/list";
        }

        return "redirect:/fees";
    }

    @PostMapping("/payments/{id}/cancel")
    public String cancelPayment(@PathVariable Long id) {
        feePaymentService.cancelPayment(id);

        return "redirect:/fees";
    }

    @PostMapping("/generate/monthly")
    public String generateMonthlyPayments(Model model) {
        try {
            feePaymentService.generateMonthlyPayments();
        } catch (IllegalArgumentException e) {
            loadFeePage(model, null);
            model.addAttribute("feePayment", new FeePaymentRequest());
            model.addAttribute("feePaymentActionError", e.getMessage());

            return "fee/list";
        }

        return "redirect:/fees";
    }

    @PostMapping("/generate/yearly")
    public String generateYearlyPayments(Model model) {
        try {
            feePaymentService.generateYearlyPayments();
        } catch (IllegalArgumentException e) {
            loadFeePage(model, null);
            model.addAttribute("feePayment", new FeePaymentRequest());
            model.addAttribute("feePaymentActionError", e.getMessage());

            return "fee/list";
        }

        return "redirect:/fees";
    }
    @PostMapping("/generate/parking")
    public String generateParkingMonthlyPayments(Model model) {
        try {
            feePaymentService.generateParkingMonthlyPayments();
        } catch (IllegalArgumentException e) {
            loadFeePage(model, null);
            model.addAttribute("feePayment", new FeePaymentRequest());
            model.addAttribute("feePaymentActionError", e.getMessage());
            return "fee/list";
        }

        return "redirect:/fees";
    }

    private void loadFeePage(Model model, String keyword) {
        List<FeePaymentResponse> payments;

        if (keyword != null && !keyword.trim().isEmpty()) {
            payments = feePaymentService.search(keyword.trim());
        } else {
            payments = feePaymentService.getAll();
        }

        model.addAttribute("payments", payments);
        model.addAttribute("activeFeeTypes", feeTypeService.getActiveFeeTypes());
        model.addAttribute("households", householdService.getAll());
        model.addAttribute("keyword", keyword);
    }

    private void loadFeeTypePage(Model model, String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("feeTypes", feeTypeService.search(keyword.trim()));
        } else {
            model.addAttribute("feeTypes", feeTypeService.getAll());
        }

        model.addAttribute("keyword", keyword);
    }
}