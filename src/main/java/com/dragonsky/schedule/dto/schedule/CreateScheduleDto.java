package com.dragonsky.schedule.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateScheduleDto {
    @NotBlank(message = "할일 제목은 필수 입니다.")
    @Schema(description = "할일 제목",example = "JPA 공부")
    private String title;

    @NotBlank(message = "할일 내용은 필수 입니다.")
    @Schema(description = "할일 내용",example = "Spring 공부 & JPA 공부")
    private String content;
}
