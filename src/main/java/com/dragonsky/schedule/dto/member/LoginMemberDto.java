package com.dragonsky.schedule.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginMemberDto {
    @NotBlank(message = "username은 필수 값입니다.")
    private String username;
    @NotBlank(message = "password는 필수 값입니다.")
    private String password;
}
