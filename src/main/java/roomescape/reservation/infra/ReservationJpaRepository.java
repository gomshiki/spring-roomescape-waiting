package roomescape.reservation.infra;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTimeWithStatus;
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

    @Deprecated
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
            SELECT new roomescape.reservation.domain.ReservationTimeWithStatus
            (rt.id, rt.startAt, CASE 
                                   WHEN r.id IS NOT NULL 
                                       THEN TRUE 
                                   ELSE FALSE 
                               END
            )
            FROM ReservationTime rt
                LEFT JOIN Reservation r ON rt.id = r.reservationTime.id
                AND r.reservationTheme.id = :themeId
                AND r.date = :date
            WHERE r.status = '예약' OR r.status IS NULL
            """)
    List<ReservationTimeWithStatus> findReservationTimesWithStatus(@Param("date") String date, @Param("themeId") Long themeId);

    @Query("""
            SELECT r FROM Reservation r
            JOIN FETCH r.reservationTime
            JOIN FETCH r.reservationTheme
            WHERE r.id = :id
            """)
    List<Reservation> findByIdWithDetails(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            (SELECT r.id AS id, r.name AS name, r.date AS date, rt.start_at AS startAt, th.name AS themeName, r.status AS status
            FROM Reservation r
            JOIN reservation_time rt ON r.time_id = rt.id
            JOIN theme th ON r.theme_id = th.id
            WHERE r.name = :name)
            UNION ALL
            (SELECT w.id AS id, w.name AS name, w.date AS date, rt.start_at AS startAt, th.name AS themeName, w.status AS status
            FROM Waiting w
            JOIN reservation_time rt ON w.time_id = rt.id
            JOIN theme th ON w.theme_id = th.id
            WHERE w.name = :name)
            """)
    List<ReservationAndWaitingProjection> findByNameWithDetails(@Param("name") String name);
}
