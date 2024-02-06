package com.dragonsky.schedule.service;

import com.dragonsky.schedule.dto.comment.*;
import com.dragonsky.schedule.dto.schedule.*;
import com.dragonsky.schedule.entity.Comment;
import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import com.dragonsky.schedule.repository.CommentRepository;
import com.dragonsky.schedule.repository.MemberRepository;
import com.dragonsky.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateScheduleResponseDto createSchedule(CreateScheduleDto createScheduleDto, Member member){

        Schedule schedule = new Schedule(createScheduleDto,member);
        Schedule scheduleSave = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(scheduleSave,member);
    }

    public List<GetSchedulesResponseDto> getSchedules(Member member){
        return scheduleRepository.findByMember(member)
                .stream()
                .map(schedule -> new GetSchedulesResponseDto(schedule))
                .toList();
    }



    public GetScheduleResponseDto getSchedule(Long scheduleId, Member member) {
        Schedule schedule =  scheduleRepository.findByMemberAndId(member,scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 포스터입니다."));

        return new GetScheduleResponseDto(schedule,member);
    }

    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long scheduleId, UpdateScheduleDto updateScheduleRequestDto, Member member) {
        Schedule schedule =  scheduleRepository.findByMemberAndId(member,scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 포스터입니다."));

        if(member.getId() != schedule.getMember().getId()){
            new IllegalArgumentException("게시글의 작성자만 접근할 수 있습니다.");
        }

        schedule.updateSchedule(updateScheduleRequestDto);

        return new UpdateScheduleResponseDto(schedule,member);
    }

    @Transactional
    public void updateDoneSchedule(Long scheduleId, Member member) {
        Schedule schedule =  scheduleRepository.findByMemberAndId(member,scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 포스터입니다."));

        if(member.getId() != schedule.getMember().getId()){
            new IllegalArgumentException("게시글의 작성자만 접근할 수 있습니다.");
        }

        schedule.setDone();

    }

    @Transactional
    public CreateScheduleCommentResponseDto createScheduleComment(Long scheduleId, CreateCommentDto createCommentDto, Member member) {
        Schedule schedule =  scheduleRepository.findByMemberAndId(member,scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 포스터입니다."));

        if(member.getId() != schedule.getMember().getId()){
            new IllegalArgumentException("게시글의 작성자만 접근할 수 있습니다.");
        }

        Comment comment = new Comment(schedule,createCommentDto,member);
        commentRepository.save(comment);

        return new CreateScheduleCommentResponseDto(member,schedule,comment);
    }

    @Transactional
    public UpdateScheduleCommentResponseDto updateScheduleComment(Long scheduleId, Long commentId,UpdateScheduleCommentDto updateScheduleCommentDto, Member member) {
        Schedule schedule =  scheduleRepository.findByMemberAndId(member,scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 포스터입니다."));

        Comment comment = schedule.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 ID에 해당하는 댓글을 스케줄에서 찾을 수 없거나 작성자가 일치하지 않습니다: " + commentId));

        comment.setComment(updateScheduleCommentDto.getComment());

        return new UpdateScheduleCommentResponseDto(member,schedule,comment);
    }

    @Transactional
    public void deleteScheduleComment(Long scheduleId, Long commentId, Member member) {
        Schedule schedule =  scheduleRepository.findByMemberAndId(member,scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 포스터입니다."));


        Comment comment = schedule.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 ID에 해당하는 댓글을 스케줄에서 찾을 수 없거나 작성자가 일치하지 않습니다: " + commentId));

        comment.removeComment();
        commentRepository.delete(comment);
    }

    public List<GetSchedulesResponseDto> getNotDoneSchedules(Member member) {
        return scheduleRepository.findByMemberAndDoneIsFalseOrderByCreateAtDesc(member)
                .stream()
                .map(schedule -> new GetSchedulesResponseDto(schedule))
                .toList();
    }


    public List<GetSchedulesResponseDto> getByTitleSchedules(Member member, String keyword) {
        return scheduleRepository.findByMemberAndTitleContainingOrderByCreateAtDesc(member,keyword)
                .stream()
                .map(schedule -> new GetSchedulesResponseDto(schedule))
                .toList();
    }
}
