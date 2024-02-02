package com.dragonsky.schedule.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class CreateMemberDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "username는 8~10자 소문자, 숫자를 사용하세요.")
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,15}$", message = "password는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
}
