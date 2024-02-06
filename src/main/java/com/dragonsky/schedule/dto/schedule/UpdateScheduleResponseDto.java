package com.dragonsky.schedule.dto.schedule;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponseDto {

    @Schema(description = "할일 제목",example = "JPA 공부")
    private String title;

    @Schema(description = "할일 내용",example = "Spring 공부 & JPA 공부")
    private String content;

    @Schema(description = "완료 여부",example = "JPA 공부")
    private boolean done;

    @Schema(description = "작성일",example = "2024-02-02T12:10:36.458852")
    private LocalDateTime createAt;

    @Schema(description = "수정일",example = "2024-02-03T12:10:36.458852")
    private LocalDateTime updateAt;

    @Schema(description = "작성자",example = "dragonsky")
    private String member;


    public UpdateScheduleResponseDto(Schedule schedule, Member member){
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createAt = schedule.getCreateAt();
        this.updateAt = schedule.getModifiedAt();
        this.member = member.getUsername();
    }
}
