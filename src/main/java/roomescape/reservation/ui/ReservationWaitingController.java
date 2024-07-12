package roomescape.reservation.ui;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.member.dto.MemberRequestDto;
import roomescape.member.infra.LoginMember;
import roomescape.reservation.application.ReservationWaitingService;
import roomescape.reservation.dto.ReservationWaitingRequestDto;
import roomescape.reservation.dto.ReservationWaitingResponseDto;
import roomescape.reservation.infra.WaitingStatus;

@RestController
public class ReservationWaitingController {

    private final ReservationWaitingService reservationWaitingService;

    public ReservationWaitingController(ReservationWaitingService reservationWaitingService) {
        this.reservationWaitingService = reservationWaitingService;
    }

    @PostMapping("/reservation/waiting")
    @WaitingStatus("예약대기")
    public ResponseEntity<ReservationWaitingResponseDto> createReservationWaiting(
            @LoginMember MemberRequestDto memberRequestDto,
            @Valid @RequestBody ReservationWaitingRequestDto reservationWaitingRequestDto
    ) {
        reservationWaitingRequestDto.assignName(memberRequestDto.getName());
        ReservationWaitingResponseDto savedWaitingReservation = reservationWaitingService.save(reservationWaitingRequestDto);

        return ResponseEntity.ok().body(savedWaitingReservation);
    }
}
