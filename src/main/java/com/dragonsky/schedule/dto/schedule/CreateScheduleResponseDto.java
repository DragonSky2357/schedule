package com.dragonsky.schedule.dto.schedule;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateScheduleResponseDto {
    private String title;
    private String content;
    private boolean done;
    private String member;
    public CreateScheduleResponseDto(Schedule schedule,Member member) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.done = schedule.isDone();
        this.member = member.getUsername();
    }
}
