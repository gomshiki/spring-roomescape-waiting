package roomescape.reservationtime.infra;

import roomescape.reservationtime.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<ReservationTime> findById(Long id);
}
