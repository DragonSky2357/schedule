package com.dragonsky.schedule.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginMemberDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
