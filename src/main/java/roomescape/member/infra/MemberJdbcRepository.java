package roomescape.member.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import roomescape.member.domain.Member;

import java.util.List;
import java.util.Optional;

public class MemberJdbcRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> rowMapper;

    public MemberJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (resultSet, rowNum) -> new Member(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("role")
        );
    }

    public boolean existsByEmailAndPassword(String email, String password) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM member WHERE email = ? and password = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, email, password);
    }

    public Optional<Long> findIdByEmailAndPassword(String email, String password) {
        final String sql = "SELECT id FROM member WHERE email = ? AND password = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Long.class, email, password));
    }

    public Optional<String> findNameById(long id) {
        final String sql = "SELECT name FROM member WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, String.class, id));
    }

    public Optional<Member> findById(long id) {
        final String sql = "SELECT * FROm member WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public List<Member> findAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
