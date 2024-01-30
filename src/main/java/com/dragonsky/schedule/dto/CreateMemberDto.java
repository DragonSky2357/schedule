package com.dragonsky.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMemberDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
