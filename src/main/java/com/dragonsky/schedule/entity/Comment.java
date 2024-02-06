package com.dragonsky.schedule.entity;

import com.dragonsky.schedule.dto.comment.CreateCommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public Comment(Schedule schedule, CreateCommentDto createCommentDto, Member member) {
        this.comment = createCommentDto.getComment();
        this.schedule = schedule;
        this.member = member;
    }

    public void removeComment(){
        this.schedule.getComments().remove(this);
        this.member.getComments().remove(this);
    }

}
