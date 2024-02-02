package com.dragonsky.schedule.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentDto {
    @NotBlank
    private String comment;
}
