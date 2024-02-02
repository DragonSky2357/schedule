package com.dragonsky.schedule.controller;

import com.dragonsky.schedule.common.dto.ResponseDto;
import com.dragonsky.schedule.dto.comment.*;
import com.dragonsky.schedule.dto.schedule.*;
import com.dragonsky.schedule.security.UserDetailsImpl;
import com.dragonsky.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
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

    @PostMapping("/schedule")
    public ResponseEntity createSchedule(@RequestBody @Valid CreateScheduleDto createScheduleDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CreateScheduleResponseDto result = scheduleService.createSchedule(createScheduleDto, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(), result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

    @GetMapping("/schedule")
    public ResponseEntity getSchedules(@RequestParam(value = "hideCompleted", defaultValue = "false") boolean hideCompleted,
                                       @RequestParam(value = "title", defaultValue = "") String title,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<GetSchedulesResponseDto> result;
            if (hideCompleted) {
                result = scheduleService.getNotDoneSchedules(userDetails.getUser());
            } else if (!title.isEmpty())
                result = scheduleService.getByTitleSchedules(userDetails.getUser(), title);
            else {
                result = scheduleService.getSchedules(userDetails.getUser());
            }
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }
    @GetMapping("/schedule/{id}")
    public ResponseEntity getSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            GetScheduleResponseDto result = scheduleService.getSchedule(id, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

    @PatchMapping("/schedule/{id}")
    public ResponseEntity updateSchedule(@PathVariable Long id, @RequestBody @Valid UpdateScheduleDto updateScheduleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            UpdateScheduleResponseDto result = scheduleService.updateSchedule(id, updateScheduleRequestDto, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

    @PatchMapping("/schedule/{id}/done")
    public ResponseEntity updateDoneSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            scheduleService.updateDoneSchedule(id, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.success(HttpStatus.NO_CONTENT.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

    @PostMapping("/schedule/{id}/comment")
    public ResponseEntity createScheduleComment(@PathVariable Long id, @RequestBody @Valid CreateCommentDto createCommentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CreateScheduleCommentResponseDto result = scheduleService.createScheduleComment(id, createCommentDto, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(), result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

    @PatchMapping("/schedule/{scheduleId}/comment/{commentId}")
    public ResponseEntity updateScheduleComment(@PathVariable Long scheduleId, @PathVariable Long commentId,
                                                @RequestBody @Valid UpdateScheduleCommentDto updateScheduleCommentDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            UpdateScheduleCommentResponseDto result = scheduleService.updateScheduleComment(scheduleId, commentId, updateScheduleCommentDto, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/schedule/{scheduleId}/comment/{commentId}")
    public ResponseEntity deleteScheduleComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            scheduleService.deleteScheduleComment(scheduleId, commentId, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), "댓글 삭제 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

//    @GetMapping("/schedule/{scheduleId}/notdone")
//    public ResponseEntity getNotDoneSchdule(@PathVariable Long scheduleId,@AuthenticationPrincipal UserDetailsImpl userDetails ){
//        try {
//            List<GetNotDoneResponseDto> result = scheduleService.getNotDoneSchdule(scheduleId,userDetails.getUser());
//            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(),result));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
//                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
//        }
//    }
}
