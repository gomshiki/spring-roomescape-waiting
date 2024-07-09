package roomescape.reservation.infra;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.List;

public interface ReservationJpaRepository extends CrudRepository<Reservation, Long>, ReservationRepository {

    Reservation save(Reservation reservation);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.reservationTime " +
            "JOIN FETCH r.reservationTheme")
    List<Reservation> findAllWithDetails();

    void deleteById(final Long id);

    boolean existsById(final Long id);

    @Query("""
            SELECT rt
            FROM ReservationTime rt
            WHERE rt.id NOT IN (
                    SELECT r.reservationTime.id
                    FROM Reservation r
                    WHERE r.date = :date AND r.reservationTheme.id = :themeId)
            """)
    List<ReservationTime> getAvailableReservationTimes(@Param("date") String date, @Param("themeId") Long themeId);

    @Query("""
            SELECT r FROM Reservation r
            JOIN FETCH r.reservationTime
            JOIN FETCH r.reservationTheme
            WHERE r.id = :id
            """)
    List<Reservation> findByIdWithDetails(@Param("id") Long id);

    @Query("""
            SELECT r FROM Reservation r
            JOIN FETCH r.reservationTime
            JOIN FETCH r.reservationTheme
            WHERE r.name = :name
            """)
    List<Reservation> findByNameWithDetails(@Param("name") String name);
}
