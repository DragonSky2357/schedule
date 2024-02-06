package com.dragonsky.schedule.controller;

import com.dragonsky.schedule.common.dto.ResponseDto;
import com.dragonsky.schedule.dto.member.CreateMemberDto;
import com.dragonsky.schedule.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid CreateMemberDto createMemberDto){
        memberService.createMember(createMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(),"회원 가입 성공"));
    }
}
