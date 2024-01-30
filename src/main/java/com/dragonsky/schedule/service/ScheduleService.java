package com.dragonsky.schedule.service;

import com.dragonsky.schedule.dto.schedule.CreateScheduleDto;
import com.dragonsky.schedule.dto.schedule.CreateScheduleResponseDto;
import com.dragonsky.schedule.dto.schedule.GetScheduleResponseDto;
import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import com.dragonsky.schedule.repository.MemberRepository;
import com.dragonsky.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    public CreateScheduleResponseDto createSchedule(CreateScheduleDto createScheduleDto, Member member){
        Member findMember = memberRepository.findByUsername(member.getUsername())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않은 유저입니다."));

        Schedule schedule = new Schedule(createScheduleDto,findMember);
        Schedule scheduleSave = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(scheduleSave,findMember);
    }

    public List<GetScheduleResponseDto> getSchedule(Member member){
        Member findMember = memberRepository.findByUsername(member.getUsername())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않은 유저입니다."));

        return scheduleRepository.findByMemberOrderByCreateAtDesc(findMember)
                .stream()
                .map(schedule -> new GetScheduleResponseDto(schedule,findMember))
                .toList();
    }
}
