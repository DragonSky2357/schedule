package com.dragonsky.schedule.entity;

import com.dragonsky.schedule.dto.schedule.CreateScheduleDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    public Schedule(CreateScheduleDto createScheduleDto, Member member){
        this.title = createScheduleDto.getTitle();
        this.content = createScheduleDto.getContent();
        this.member = member;
        member.getScheduleList().add(this);
    }
}
