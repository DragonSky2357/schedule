package com.dragonsky.schedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateScheduleDto {
    @NotBlank(message = "할일 제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "할일 내용은 필수 입니다.")
    private String content;
}
