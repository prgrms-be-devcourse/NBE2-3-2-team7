package com.project.popupmarket.dto.popup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PopupStoreTO {
    private Long id;                // ID
    private Long customerId;        // 고객 ID
    private String type;            // 유형
    private String zipcode;         // 우편번호
    private String address;         // 주소
    private String addrDetail;      // 상세 주소
    private String title;           // 제목
    private String description;     // 설명
    private LocalDate startDate;    // 시작 날짜
    private LocalDate endDate;      // 종료 날짜
    private String ageGroup;        // 연령대 (Enum -> String)
    private LocalDateTime registeredAt; // 등록 날짜
    private String status;          // 상태 (Enum -> String)

    // 생성자
    public PopupStoreTO(Long id, Long customerId, String type, String zipcode, String address,
                        String addrDetail, String title, String description, LocalDate startDate,
                        LocalDate endDate, String ageGroup, LocalDateTime registeredAt, String status) {
        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.zipcode = zipcode;
        this.address = address;
        this.addrDetail = addrDetail;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ageGroup = ageGroup;
        this.registeredAt = registeredAt;
        this.status = status;
    }
}
