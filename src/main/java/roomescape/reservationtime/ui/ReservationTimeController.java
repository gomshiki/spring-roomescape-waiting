package roomescape.reservationtime.ui;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservationtime.application.ReservationTimeService;
import roomescape.reservationtime.dto.ReservationTimeRequestDto;
import roomescape.reservationtime.dto.ReservationTimeResponseDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getTimes() {
        final List<ReservationTimeResponseDto> reservationTimeResponseDtos = reservationTimeService.getTimes();
        return ResponseEntity.ok().body(reservationTimeResponseDtos);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> addTime(
            final @Valid @RequestBody ReservationTimeRequestDto reservationTimeRequestDto
    ) throws URISyntaxException
    {
        final ReservationTimeResponseDto time = reservationTimeService.addTime(reservationTimeRequestDto);
        return ResponseEntity.created(new URI("/times/" + time.getTimeId())).body(time);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(final @PathVariable Long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
