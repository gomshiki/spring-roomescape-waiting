package roomescape.member.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.member.application.AuthService;
import roomescape.member.dto.LoginMemberRequestDto;
import roomescape.member.dto.MemberResponseDto;
import roomescape.member.dto.TokenResponseDto;

import static roomescape.member.infra.TokenUtil.extractTokenFromCookie;

@RestController
public class LoginMemberController {

    private static final Logger log = LoggerFactory.getLogger(LoginMemberController.class);
    private final AuthService authService;

    public LoginMemberController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(final @Valid @RequestBody LoginMemberRequestDto loginMemberRequestDto,
                                      final HttpServletResponse response) {
        log.info("loginMemberID : {}", loginMemberRequestDto.getEmail());
        final TokenResponseDto tokenResponseDto = authService.createToken(loginMemberRequestDto);

        final Cookie cookie = new Cookie("token", tokenResponseDto.getAccessToken());
        cookie.setMaxAge(1800);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("login/check")
    public ResponseEntity<MemberResponseDto> getMember(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        final String token = extractTokenFromCookie(cookies);
        final MemberResponseDto memberResponseDto = authService.findMemberName(token);
        log.info("로그인 계정의 Name : {}", memberResponseDto.getName());
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<MemberResponseDto> logout(
            final HttpServletRequest request, final HttpServletResponse response
    ) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        request.getSession().invalidate();

        return ResponseEntity.ok().build();
    }
}
