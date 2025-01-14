package com.project.popupmarket.entity;

import com.project.popupmarket.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "receipts")
public class Receipts {
    @Id
    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "rental_land_id", nullable = false)
    private Long rentalLandId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "amount", precision = 10)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ReservationStatus reservationStatus;

    @Column(name = "reserved_at", nullable = false, updatable = false)
    private LocalDateTime reservedAt = LocalDateTime.now();
}