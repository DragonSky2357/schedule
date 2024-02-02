package com.dragonsky.schedule.repository;

import com.dragonsky.schedule.entity.Comment;
import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
