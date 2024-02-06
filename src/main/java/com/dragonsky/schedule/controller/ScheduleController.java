package com.dragonsky.schedule.controller;

import com.dragonsky.schedule.common.dto.ResponseDto;
import com.dragonsky.schedule.dto.comment.*;
import com.dragonsky.schedule.dto.schedule.*;
import com.dragonsky.schedule.security.UserDetailsImpl;
import com.dragonsky.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity createSchedule(@RequestBody @Valid CreateScheduleDto createScheduleDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CreateScheduleResponseDto result = scheduleService.createSchedule(createScheduleDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(), result));
    }

    @GetMapping("/schedules")
    public ResponseEntity getSchedules(@RequestParam(value = "hideCompleted") boolean hideCompleted,
                                       @RequestParam(value = "title") String title,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<GetSchedulesResponseDto> result;
        if (hideCompleted) {
            result = scheduleService.getNotDoneSchedules(userDetails.getUser());
        } else if (!title.isEmpty())
            result = scheduleService.getByTitleSchedules(userDetails.getUser(), title);
        else {
            result = scheduleService.getSchedules(userDetails.getUser());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity getSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GetScheduleResponseDto result = scheduleService.getSchedule(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity updateSchedule(@PathVariable Long id, @RequestBody @Valid UpdateScheduleDto updateScheduleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UpdateScheduleResponseDto result = scheduleService.updateSchedule(id, updateScheduleRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @PatchMapping("/schedules/{id}/done")
    public ResponseEntity updateDoneSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.updateDoneSchedule(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.success(HttpStatus.NO_CONTENT.value()));
    }

    @PostMapping("/schedules/{id}/comments")
    public ResponseEntity createScheduleComment(@PathVariable Long id, @RequestBody @Valid CreateCommentDto createCommentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CreateScheduleCommentResponseDto result = scheduleService.createScheduleComment(id, createCommentDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(), result));
    }

    @PatchMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity updateScheduleComment(@PathVariable Long scheduleId, @PathVariable Long commentId,
                                                @RequestBody @Valid UpdateScheduleCommentDto updateScheduleCommentDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UpdateScheduleCommentResponseDto result = scheduleService.updateScheduleComment(scheduleId, commentId, updateScheduleCommentDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity deleteScheduleComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteScheduleComment(scheduleId, commentId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), "댓글 삭제 성공"));
    }
}
