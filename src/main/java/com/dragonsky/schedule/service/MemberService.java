package com.dragonsky.schedule.service;

import com.dragonsky.schedule.dto.member.CreateMemberDto;
import com.dragonsky.schedule.entity.Member;
import com.dragonsky.schedule.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public void createMember(CreateMemberDto createMemberDto){
        String username = createMemberDto.getUsername();
        String password = passwordEncoder.encode(createMemberDto.getPassword());

        Optional<Member> findMember =  memberRepository.findByUsername(username);

        if(findMember.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }

        Member member = new Member(username,password);
        memberRepository.save(member);
    }
}
