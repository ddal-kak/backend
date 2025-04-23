package ddalkak.member.controller;

import ddalkak.member.common.exception.DuplicatedEmailException;
import ddalkak.member.domain.entity.Member;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.dto.response.SignUpResponse;
import ddalkak.member.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    /**
     * API 응답 스펙 변경을 고려해 Member 전체를 서비스 레이어에서 받음,필요한 필드만 응답 Dto로 생성
     * @param request 회원가입 요청 정보(이름, 이메일, 비밀번호)
     * @return 등록된 회원의 ID를 포함한 응답 객체
     * @throws DuplicatedEmailException 이미 등록된 이메일일 경우 발생
     * @throws MethodArgumentNotValidException request 유효성 검증 실패 시 발생
     * @throws DataIntegrityViolationException 동시성 문제로 인한 유니크 제약 조건 위배 시 발생
     */
    @PostMapping
    public ResponseEntity<SignUpResponse> signup(@Valid @RequestBody final SignUpRequest request) {
        Member newMember = memberService.signup(request);
        return ResponseEntity.ok()
                .body(new SignUpResponse(newMember.getMemberId()));
    }
}
