package com.dragonsky.schedule.dto.comment;

import com.dragonsky.schedule.entity.Comment;
import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleCommentResponseDto {
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String member;
    private String comment;

    public UpdateScheduleCommentResponseDto(Member member, Schedule schedule, Comment comment) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createAt = schedule.getCreateAt();
        this.updateAt = schedule.getModifiedAt();
        this.member = schedule.getMember().getUsername();
        this.comment = comment.getComment();
    }
}
