package roomescape.reservation.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import roomescape.member.application.AuthService;
import roomescape.member.dto.MemberResponseDto;
import roomescape.member.infra.TokenUtil;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.dto.ReservationMineResponseDto;

import java.util.List;

@RestController
public class ReservationMineController {

    private final ReservationService reservationService;
    private final AuthService authService;

    public ReservationMineController(ReservationService reservationService, AuthService authService) {
        this.reservationService = reservationService;
        this.authService = authService;
    }

    @GetMapping("/reservations-mine")
    public ResponseEntity<List<ReservationMineResponseDto>> getMineReservations(
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String token = TokenUtil.extractTokenFromCookie(cookies);

        MemberResponseDto foundMember = authService.findMember(token);
        List<ReservationMineResponseDto> mineResponseDtos = reservationService.findAllReservationByName(
                foundMember.getName()
        );

        return ResponseEntity.ok().body(mineResponseDtos);
    }
}
