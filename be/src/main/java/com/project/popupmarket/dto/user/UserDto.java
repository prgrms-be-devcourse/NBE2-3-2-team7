package com.project.popupmarket.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String brand;
    private String profileImage;
    private String tel;
}
