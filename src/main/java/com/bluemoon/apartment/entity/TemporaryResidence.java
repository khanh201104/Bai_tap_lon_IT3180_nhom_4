package com.bluemoon.apartment.entity;

import com.bluemoon.apartment.constant.TemporaryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "temporary_residences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporaryResidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @Enumerated(EnumType.STRING)
    private TemporaryType type;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;
}
