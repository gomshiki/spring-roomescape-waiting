package roomescape.reservation.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> reservationRowMapper;
    private final RowMapper<ReservationTime> reservationTimeRowMapper;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        this.reservationRowMapper = (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getString("reservation_date"),
                new ReservationTime
                        (
                                resultSet.getLong("time_id"),
                                resultSet.getString("start_at")
                        ),
                new ReservationTheme
                        (
                                resultSet.getLong("theme_id"),
                                resultSet.getString("theme_name"),
                                resultSet.getString("theme_description"),
                                resultSet.getString("theme_thumbnail")
                        ),
                resultSet.getString("status")
        );
        this.reservationTimeRowMapper = (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("reservation_time_id"),
                resultSet.getString("start_at")
        );

    }

    public List<Reservation> findAllWithDetails() {
        final String sql = """
                SELECT r.id AS reservation_id, 
                       r.name AS reservation_name, 
                       r.date AS reservation_date, 
                       r.status AS status,
                       t.id AS time_id, 
                       t.start_at AS start_at,
                       th.id AS theme_id,
                       th.name AS theme_name,      
                       th.description AS theme_description,      
                       th.thumbnail AS theme_thumbnail      
                FROM reservation AS r 
                INNER JOIN reservation_time AS t ON r.time_id = t.id
                INNER JOIN theme AS th ON r.theme_id = th.id
                """;

        return jdbcTemplate.query(sql, reservationRowMapper);

    }

    public Reservation save(Reservation reservation) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getReservationTime().getId());
        parameters.put("theme_id", reservation.getReservationTheme().getId());
        long savedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Reservation(
                savedId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime(),
                reservation.getReservationTheme(),
                reservation.getStatus()
        );
    }

    public void deleteById(Long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsById(Long id) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM reservation WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public List<ReservationTime> getAvailableReservationTimes(String date, Long themeId) {
        String sql = """
                SELECT rt.id AS reservation_time_id, rt.start_at AS start_at
                FROM reservation_time rt
                WHERE rt.id NOT IN (
                    SELECT r.time_id
                    FROM reservation r
                    WHERE r.date = ? AND r.theme_id = ?
                )
                """;
        return jdbcTemplate.query(sql, reservationTimeRowMapper, date, themeId);
    }

    public List<Reservation> findByIdWithDetails(Long id) {
        final String sql = """
                SELECT r.id AS reservation_id, 
                        r.name AS reservation_name, 
                        r.date AS reservation_date, 
                        r.status AS status, 
                        t.id AS time_id, 
                        t.start_at AS start_at,
                        th.id  AS theme_id,
                        th.name AS theme_name,
                        th.description AS theme_description,
                        th.thumbnail AS theme_thumbnail
                FROM reservation AS r 
                INNER JOIN reservation_time AS t ON r.time_id = t.id
                INNER JOIN theme AS th ON r.theme_id = th.id
                WHERE r.id = ?
                """;

        return jdbcTemplate.query(sql, reservationRowMapper, id);
    }
    public List<Reservation> findByNameWithDetails(String name) {
        final String sql = """
                SELECT r.id AS reservation_id, 
                        r.name AS reservation_name, 
                        r.date AS reservation_date, 
                        r.status AS status, 
                        t.id AS time_id, 
                        t.start_at AS start_at,
                        th.id  AS theme_id,
                        th.name AS theme_name,
                        th.description AS theme_description,
                        th.thumbnail AS theme_thumbnail
                FROM reservation AS r 
                INNER JOIN reservation_time AS t ON r.time_id = t.id
                INNER JOIN theme AS th ON r.theme_id = th.id
                WHERE r.name = ?
                """;

        return jdbcTemplate.query(sql, reservationRowMapper, name);
    }
}
