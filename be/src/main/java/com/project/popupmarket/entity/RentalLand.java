package com.project.popupmarket.entity;

import com.project.popupmarket.enums.ActivateStatus;
import com.project.popupmarket.enums.AgeGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "rental_land")
public class RentalLand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "landlord_id", nullable = false)
    private Long landlordId;

    @Column(name = "price", precision = 10, nullable = false)
    private BigDecimal price;

    @Size(min = 5, max = 5)
    @Column(name = "zipcode", length = 5, nullable = false)
    private String zipcode;

    @Size(max = 255)
    @Column(name = "address", nullable = false)
    private String address;

    @Size(max = 255)
    @Column(name = "addr_detail", nullable = false)
    private String addrDetail;

    @Size(max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Size(max = 255)
    @Column(name = "infra")
    private String infra;

    @Column(name = "area", nullable = false)
    private Integer area;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", nullable = false)
    private AgeGroup ageGroup;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDateTime registeredAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ActivateStatus status;
}