package com.dragonsky.schedule.dto.schedule;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetNotDoneResponseDto {
    private String title;
    private String content;
    private LocalDateTime createAt;
    private String member;
    public GetNotDoneResponseDto(Schedule schedule, Member member) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createAt = schedule.getCreateAt();
        this.member = member.getUsername();
    }

}
