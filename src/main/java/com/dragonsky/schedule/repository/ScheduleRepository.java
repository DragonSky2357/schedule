package com.dragonsky.schedule.repository;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByMember(Member member);
    List<Schedule> findByMemberOrderByCreateAtDesc(Member member);

}
