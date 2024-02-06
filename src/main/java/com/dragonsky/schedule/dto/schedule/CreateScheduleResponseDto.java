package com.dragonsky.schedule.dto.schedule;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateScheduleResponseDto {
    @Schema(description = "할일 제목",example = "JPA 공부")
    private String title;

    @Schema(description = "할일 내용",example = "Spring 공부 & JPA 공부")
    private String content;

    @Schema(description = "완료 여부",example = "false")
    private boolean done;

    @Schema(description = "작성자",example = "dragonsky")
    private String member;
    public CreateScheduleResponseDto(Schedule schedule,Member member) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.done = schedule.isDone();
        this.member = member.getUsername();
    }
}
