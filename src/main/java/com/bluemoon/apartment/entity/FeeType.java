package com.bluemoon.apartment.entity;

import com.bluemoon.apartment.constant.FeeCalculationType;
import com.bluemoon.apartment.constant.FeeCycleType;
import com.bluemoon.apartment.constant.FeeGroup;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fee_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_group", nullable = false, length = 50)
    private FeeGroup feeGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "cycle_type", nullable = false, length = 50)
    private FeeCycleType cycleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculation_type", nullable = false, length = 50)
    private FeeCalculationType calculationType;

    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "active", nullable = false)
    private Boolean active;
}