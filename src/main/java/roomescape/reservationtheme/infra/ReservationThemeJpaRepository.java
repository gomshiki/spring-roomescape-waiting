package roomescape.reservationtheme.infra;

import org.springframework.data.repository.CrudRepository;
import roomescape.reservationtheme.domain.ReservationTheme;

import java.util.List;
import java.util.Optional;

public interface ReservationThemeJpaRepository extends CrudRepository<ReservationTheme, Long>, ReservationThemeRepository {

    List<ReservationTheme> findAll();

    ReservationTheme save(ReservationTheme reservationTheme);

    Optional<ReservationTheme> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

}
