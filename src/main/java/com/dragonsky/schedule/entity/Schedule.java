package com.dragonsky.schedule.entity;

import com.dragonsky.schedule.dto.schedule.CreateScheduleDto;
import com.dragonsky.schedule.dto.schedule.UpdateScheduleDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean done = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "schedule")
    private List<Comment> comments = new ArrayList<>();
    public Schedule(CreateScheduleDto createScheduleDto, Member member){
        this.title = createScheduleDto.getTitle();
        this.content = createScheduleDto.getContent();
        this.member = member;
    }

    public void updateSchedule(UpdateScheduleDto updateScheduleDto){
        if(checkContainDto(updateScheduleDto.getTitle())) this.title = updateScheduleDto.getTitle();
        if(checkContainDto(updateScheduleDto.getContent())) this.content = updateScheduleDto.getContent();
    }

    private boolean checkContainDto(String data){
        return ((data != null) && (!data.isEmpty()));
    }

    public void removeComment(Comment comment){
        this.comments.remove(comment);
    }

    public void setDone() {
        this.done = !this.done;
    }
}
