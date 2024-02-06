package com.dragonsky.schedule.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentDto {
    @NotBlank(message = "메시지를 적어주세요")
    @Schema(description = "메시지",example = "열심히 합시다")
    private String comment;
}
