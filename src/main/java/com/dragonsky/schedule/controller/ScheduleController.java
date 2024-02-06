package com.dragonsky.schedule.controller;

import com.dragonsky.schedule.common.dto.ResponseDto;
import com.dragonsky.schedule.dto.comment.*;
import com.dragonsky.schedule.dto.schedule.*;
import com.dragonsky.schedule.exception.GlobalExceptionHandler;
import com.dragonsky.schedule.security.UserDetailsImpl;
import com.dragonsky.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name="Schedule Controller")
@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "스케줄 등록",description = "스케줄 등록")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "성공",
                            content = @Content(schema = @Schema(implementation = CreateScheduleResponseDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "파라미터 오류",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    @PostMapping("/schedules")
    public ResponseEntity createSchedule(@RequestBody @Valid CreateScheduleDto createScheduleDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CreateScheduleResponseDto result = scheduleService.createSchedule(createScheduleDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(), result));
    }

    @GetMapping("/schedules")
    @Operation(summary = "스케줄 조회",description = "스케줄 조회")
    @Parameter(name = "hideCompleted",required = false, example = "true")
    @Parameter(name = "title",required = false, example = "Spring")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = GetSchedulesResponseDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "파라미터 오류",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    public ResponseEntity getSchedules(@RequestParam(value = "hideCompleted",required = false) boolean hideCompleted,
                                       @RequestParam(value = "title",required = false) String title,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<GetSchedulesResponseDto> result;
        if (hideCompleted) {
            result = scheduleService.getNotDoneSchedules(userDetails.getUser());
        } else if (title != null)
            result = scheduleService.getByTitleSchedules(userDetails.getUser(), title);
        else {
            result = scheduleService.getSchedules(userDetails.getUser());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @GetMapping("/schedules/{id}")
    @Operation(summary = "특정 스케줄 조회",description = "특정 스케줄 조회")
    @Parameter(name = "id",required = true, example = "13")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = GetScheduleResponseDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "파라미터 오류",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    public ResponseEntity getSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GetScheduleResponseDto result = scheduleService.getSchedule(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @PatchMapping("/schedules/{id}")
    @Operation(summary = "특정 스케줄 업데이트",description = "특정 스케줄 업데이트")
    @Parameter(name = "id",required = true, example = "13")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = UpdateScheduleResponseDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "파라미터 오류",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    public ResponseEntity updateSchedule(@PathVariable Long id, @RequestBody @Valid UpdateScheduleDto updateScheduleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UpdateScheduleResponseDto result = scheduleService.updateSchedule(id, updateScheduleRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @PatchMapping("/schedules/{id}/done")
    @Operation(summary = "특정 스케줄 업데이트",description = "특정 스케줄 업데이트")
    @Parameter(name = "id",required = true, example = "13")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "성공",
                            content = @Content(schema = @Schema(implementation = UpdateScheduleResponseDto.class)
                            ))
            }
    )
    public ResponseEntity updateDoneSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.updateDoneSchedule(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.success(HttpStatus.NO_CONTENT.value()));
    }

    @PostMapping("/schedules/{id}/comments")
    @Operation(summary = "특정 스케줄 업데이트",description = "특정 스케줄 업데이트")
    @Parameter(name = "id",required = true, example = "13")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = UpdateScheduleResponseDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "파라미터 오류",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    public ResponseEntity createScheduleComment(@PathVariable Long id, @RequestBody @Valid CreateCommentDto createCommentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CreateScheduleCommentResponseDto result = scheduleService.createScheduleComment(id, createCommentDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(), result));
    }

    @PatchMapping("/schedules/{scheduleId}/comments/{commentId}")
    @Operation(summary = "특정 스케줄 업데이트",description = "특정 스케줄 업데이트")
    @Parameter(name = "scheduleId",required = true, example = "13")
    @Parameter(name = "commentId",required = true, example = "3")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = UpdateScheduleResponseDto.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "파라미터 오류",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    public ResponseEntity updateScheduleComment(@PathVariable Long scheduleId, @PathVariable Long commentId,
                                                @RequestBody @Valid UpdateScheduleCommentDto updateScheduleCommentDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UpdateScheduleCommentResponseDto result = scheduleService.updateScheduleComment(scheduleId, commentId, updateScheduleCommentDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    @Operation(summary = "특정 스케줄 업데이트",description = "특정 스케줄 업데이트")
    @Parameter(name = "scheduleId",required = true, example = "13")
    @Parameter(name = "commentId",required = true, example = "3")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)
                            )),
                    @ApiResponse(responseCode = "400", description = "파라미터 오류",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    public ResponseEntity deleteScheduleComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteScheduleComment(scheduleId, commentId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(), "댓글 삭제 성공"));
    }
}
