package com.project.popupmarket.entity;

import com.project.popupmarket.enums.ActivateStatus;
import com.project.popupmarket.enums.AgeGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "popup_store")
public class PopupStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "type", nullable = false)
    private String type;

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

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", nullable = false)
    private AgeGroup ageGroup;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDateTime registeredAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ActivateStatus status;
}