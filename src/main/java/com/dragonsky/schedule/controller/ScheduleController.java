package com.dragonsky.schedule.controller;

import com.dragonsky.schedule.common.dto.ResponseDto;
import com.dragonsky.schedule.dto.schedule.CreateScheduleDto;
import com.dragonsky.schedule.dto.schedule.CreateScheduleResponseDto;
import com.dragonsky.schedule.dto.schedule.GetScheduleResponseDto;
import com.dragonsky.schedule.entity.Schedule;
import com.dragonsky.schedule.security.UserDetailsImpl;
import com.dragonsky.schedule.service.ScheduleService;
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

    @PostMapping("/schedule")
    public ResponseEntity createSchedule(@RequestBody CreateScheduleDto createScheduleDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            CreateScheduleResponseDto result =  scheduleService.createSchedule(createScheduleDto,userDetails.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(),result));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(),e.getMessage()));
        }
    }


    @GetMapping("/schedule")
    public ResponseEntity getSchedule(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            List<GetScheduleResponseDto> result = scheduleService.getSchedule(userDetails.getUser());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(HttpStatus.OK.value(),result));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                    .body(ResponseDto.fail(HttpStatus.FORBIDDEN.value(),e.getMessage()));
        }
    }


}
