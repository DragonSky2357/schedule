package com.dragonsky.schedule.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter

public class UpdateScheduleDto {
    private String title;
    private String content;
}
