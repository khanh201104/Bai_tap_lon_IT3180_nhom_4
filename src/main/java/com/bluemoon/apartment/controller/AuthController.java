package com.bluemoon.apartment.controller;

import com.bluemoon.apartment.dto.request.ChangePasswordRequest;
import com.bluemoon.apartment.entity.Account;
import com.bluemoon.apartment.repository.AccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (isAuthenticated(authentication)) {
            return "redirect:/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (isAuthenticated(authentication)) {
            return "redirect:/dashboard";
        }

        return "auth/login";
    }

    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());
        return "auth/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute("changePasswordRequest") ChangePasswordRequest request,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/change-password";
        }

        Account account = accountRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())) {
            bindingResult.rejectValue("currentPassword", "error.password", "Mật khẩu hiện tại không đúng");
            return "auth/change-password";
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.password", "Xác nhận mật khẩu không khớp");
            return "auth/change-password";
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);

        model.addAttribute("successMessage", "Đổi mật khẩu thành công");
        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());

        return "auth/change-password";
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }
}