package com.project.popupmarket.dto.land;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RentalLandTO {
    private Long id;                       // ID
    private Long landlordId;               // 임대인 ID
    private BigDecimal price;              // 가격
    private String zipcode;                // 우편번호 (5자 제한)
    private String address;                // 주소
    private String addrDetail;             // 상세 주소
    private String description;            // 설명
    private String infra;                  // 인프라 (옵션)
    private String title;                  // 제목
    private Integer area;                  // 면적
    private String ageGroup;               // 연령대 (Enum -> String)
    private LocalDateTime registeredAt;    // 등록 일자 (엔티티와 동일하게 LocalDateTime으로 변경)
    private String status;                 // 활성화 상태

    // 생성자
    public RentalLandTO(Long id, Long landlordId, String zipcode,
                        BigDecimal price, String address, String addrDetail, String description,
                        String infra, String title, Integer area, String ageGroup,
                        LocalDateTime registeredAt, String status) {
        this.id = id;
        this.landlordId = landlordId;
        this.zipcode = zipcode;
        this.price = price;
        this.address = address;
        this.addrDetail = addrDetail;
        this.description = description;
        this.infra = infra;
        this.title = title;
        this.area = area;
        this.ageGroup = ageGroup;
        this.registeredAt = registeredAt;
        this.status = status;
    }
}

