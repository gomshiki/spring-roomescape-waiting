package roomescape.reservationtheme.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.reservationtheme.domain.ReservationTheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReservationThemeJdbcRepository implements ReservationThemeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTheme> reservationThemeRowMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationThemeJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationThemeRowMapper = (resultSet, rowNum) -> new ReservationTheme.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .thumbnail(resultSet.getString("thumbnail"))
                .build();
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTheme> findAll() {
        final String sql = "SELECT id, name, description, thumbnail FROM theme";
        return jdbcTemplate.query(sql, reservationThemeRowMapper);
    }

    public ReservationTheme save(final ReservationTheme theme) {
        if (theme.getId() == null) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", theme.getName());
            parameters.put("description", theme.getDescription());
            parameters.put("thumbnail", theme.getThumbnail());

            Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
            return new ReservationTheme(newId.longValue(), theme.getName(), theme.getDescription(), theme.getThumbnail());
        }

        String updateSql = "UPDATE theme SET name = ?, description = ?, thumbnail = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, theme.getName(), theme.getDescription(), theme.getThumbnail(), theme.getId());
        return theme;
    }

    public Optional<ReservationTheme> findById(final Long id) {
        final String sql = "SELECT id, name, description, thumbnail FROM theme WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reservationThemeRowMapper, id));
    }

    public boolean existsById(final Long id) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM theme WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public void deleteById(final Long id) {
        final String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
