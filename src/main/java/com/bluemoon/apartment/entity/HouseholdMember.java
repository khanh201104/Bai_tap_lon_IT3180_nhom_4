package com.bluemoon.apartment.entity;

import com.bluemoon.apartment.constant.RelationshipType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "household_members",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"household_id", "resident_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseholdMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship", nullable = false, length = 50)
    private RelationshipType relationship;

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;
}