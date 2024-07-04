package roomescape.reservationtime.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> rowMapper;

    public ReservationTimeJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        final String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public ReservationTime save(final ReservationTime reservationTime) {
        if (reservationTime.getId() == null) {
            // 새로운 엔티티 삽입
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("start_at", reservationTime.getStartAt());
            Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
            return new ReservationTime(newId.longValue(), reservationTime.getStartAt());
        }

        // 기존 엔티티 업데이트
        String updateSql = "UPDATE reservation_time SET start_at = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, reservationTime.getStartAt(), reservationTime.getId());
        return reservationTime;
    }

    public void deleteById(final Long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    public boolean existsById(final Long id) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM reservation_time WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public Optional<ReservationTime> findById(final Long id) {
        final String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }
}
