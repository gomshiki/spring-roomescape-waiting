package roomescape.reservation.infra;

import org.springframework.data.repository.query.Param;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAllWithDetails();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<ReservationTime> getAvailableReservationTimes(String date, Long themeId);

    Optional<Reservation> findByIdWithDetails(Long id);
}
