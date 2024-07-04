package roomescape.reservationtheme.infra;

import roomescape.reservationtheme.domain.ReservationTheme;

import java.util.List;
import java.util.Optional;

public interface ReservationThemeRepository {

    List<ReservationTheme> findAll();

    ReservationTheme save(ReservationTheme reservationTheme);

    Optional<ReservationTheme> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
