package com.dragonsky.schedule.controller;

import com.dragonsky.schedule.common.dto.ResponseDto;
import com.dragonsky.schedule.dto.member.CreateMemberDto;
import com.dragonsky.schedule.service.MemberService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name="Member Controller")
@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입",description = "회원 가입을 위한 controller")
    @Parameter(name="CreateMemberDto", description = "회원 가입을 위한 dto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "성공",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "이미 존재하는 유저 or 파라미터 오류")
            }
    )
    public ResponseEntity<ResponseDto> signUp(
            @Parameter(required = true, description = "회원 가입 요청")
            @RequestBody @Valid CreateMemberDto createMemberDto){
        memberService.createMember(createMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(HttpStatus.CREATED.value(),"회원 가입 성공"));
    }
}
