package com.dragonsky.schedule.dto.schedule;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetScheduleResponseDto {
    private String title;
    private String content;
    private boolean done;
    private LocalDateTime createAt;
    private String member;
    public GetScheduleResponseDto(Schedule schedule, Member findMember) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.done = schedule.isDone();
        this.createAt = schedule.getCreateAt();
        this.member = findMember.getUsername();
    }
}
