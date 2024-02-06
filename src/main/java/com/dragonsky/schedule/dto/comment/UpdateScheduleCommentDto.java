package com.dragonsky.schedule.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateScheduleCommentDto {
    @NotBlank
    @Schema(description = "메시지",example = "행복합시다.")
    private String comment;
}
