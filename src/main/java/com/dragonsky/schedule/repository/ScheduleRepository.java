package com.dragonsky.schedule.repository;

import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByMember(Member member);

    Optional<Schedule> findByMemberAndId(Member member, Long id);
    List<Schedule> findByMemberOrderByCreateAtDesc(Member member);
    List<Schedule> findByMemberAndDoneIsFalseOrderByCreateAtDesc(Member member);
    List<Schedule> findByMemberAndTitleContainingOrderByCreateAtDesc(Member member,String title);

}
