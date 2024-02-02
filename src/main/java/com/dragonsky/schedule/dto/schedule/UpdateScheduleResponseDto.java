package com.dragonsky.schedule.dto.schedule;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponseDto {
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String member;

    public UpdateScheduleResponseDto(Schedule schedule, Member member){
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createAt = schedule.getCreateAt();
        this.updateAt = schedule.getModifiedAt();
        this.member = member.getUsername();
    }
}
