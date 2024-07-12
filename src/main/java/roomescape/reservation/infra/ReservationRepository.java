package roomescape.reservation.infra;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTimeWithStatus;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAllWithDetails();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<ReservationTime> getAvailableReservationTimes(String date, Long themeId);

    List<ReservationAndWaitingProjection> findByNameWithDetails(String name);

    List<ReservationTimeWithStatus> findReservationTimesWithStatus(String date, Long themeId);
}
