package com.bluemoon.apartment.config;

import com.bluemoon.apartment.entity.Account;
import com.bluemoon.apartment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DefaultAdminInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (accountRepository.existsByUsername("admin")) {
            return;
        }

        Account admin = Account.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role("ADMIN")
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(admin);
    }
}