package roomescape.reservation.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.dto.ReservationTimeWithStatusDto;

import java.util.List;

@RestController
public class AvailableTimesController {

    private final ReservationService reservationService;

    public AvailableTimesController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/times/available")
    public ResponseEntity<List<ReservationTimeWithStatusDto>> getAvaliableTimes(
             @RequestParam("date") String date,  @RequestParam("themeId") Long themeId) {
        final List<ReservationTimeWithStatusDto> reservationTimeResponseDtos = reservationService.findAvailableTimes(date, themeId);
        return ResponseEntity.ok().body(reservationTimeResponseDtos);
    }
}
