package com.bluemoon.apartment.entity;

import com.bluemoon.apartment.constant.ResidentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "residents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String citizenId;

    private String phoneNumber;

    private String gender;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private ResidentStatus status;
}
