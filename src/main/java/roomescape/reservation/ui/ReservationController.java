package roomescape.reservation.ui;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.member.dto.MemberRequestDto;
import roomescape.member.infra.LoginMember;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        final List<ReservationResponseDto> reservationResponseDtos = reservationService.findAll();
        return ResponseEntity.ok().body(reservationResponseDtos);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(
            @Valid @RequestBody ReservationRequestDto reservationRequestDto,
            @LoginMember MemberRequestDto memberRequestDto)
    {
        reservationRequestDto.assignName(memberRequestDto.getName());
        ReservationResponseDto responseDto = reservationService.save(reservationRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
