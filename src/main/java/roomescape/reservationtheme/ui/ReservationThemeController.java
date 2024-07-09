package roomescape.reservationtheme.ui;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservationtheme.application.ReservationThemeService;
import roomescape.reservationtheme.dto.ReservationThemeRequestDto;
import roomescape.reservationtheme.dto.ReservationThemeResponseDto;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ReservationThemeController {

    private final ReservationThemeService reservationThemeService;

    public ReservationThemeController(ReservationThemeService reservationThemeService) {
        this.reservationThemeService = reservationThemeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationThemeResponseDto>> getThemes() {
        final List<ReservationThemeResponseDto> themese = reservationThemeService.getThemes();
        return ResponseEntity.ok(themese);
    }

    @PostMapping
    public ResponseEntity<ReservationThemeResponseDto> createTheme(final @Valid @RequestBody ReservationThemeRequestDto requestDto) {
        final ReservationThemeResponseDto theme = reservationThemeService.createTheme(requestDto);
        return ResponseEntity.created(URI.create("/theme/" + theme.getThemeId())).body(theme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable("id") Long id) {
        reservationThemeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
