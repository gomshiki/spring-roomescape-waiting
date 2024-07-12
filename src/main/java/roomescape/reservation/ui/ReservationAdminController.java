package roomescape.reservation.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.member.application.MemberService;
import roomescape.member.dto.MemberResponseDto;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.dto.ReservationAdminRequestDto;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.infra.ReservationStatus;

@RestController
public class ReservationAdminController {
    private final ReservationService reservationService;
    private final MemberService memberService;

    public ReservationAdminController(ReservationService reservationService, MemberService memberService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
    }

    @PostMapping("/admin/reservations")
    @ReservationStatus("예약")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody ReservationAdminRequestDto reservationAdminRequestDto
    ) {
        final MemberResponseDto foundMember = memberService.findById(reservationAdminRequestDto.getMemberId());
        final ReservationRequestDto reservationRequestDto = ReservationRequestDto
                .of(foundMember, reservationAdminRequestDto);

        final ReservationResponseDto savedReservation = reservationService.save(reservationRequestDto);

        return ResponseEntity.ok().body(savedReservation);
    }
}
