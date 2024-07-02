package roomescape.reservationtheme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import roomescape.globalfixture.entity.ReservationThemeFixture;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtheme.infra.ReservationThemeJdbcRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@TestPropertySource(locations = "classpath:test-application.yml")
@Sql(scripts = "/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationThemeRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private ReservationThemeJdbcRepository reservationThemeJdbcRepository;

    @BeforeEach
    void setUp(){
        reservationThemeJdbcRepository = new ReservationThemeJdbcRepository(jdbcTemplate);
    }

    @DisplayName("테마를 저장할 수 있습니다.")
    @Test
    void save() {
        //given
        final ReservationTheme request = ReservationThemeFixture.createReservationTheme();

        //when
        final ReservationTheme savedTheme = reservationThemeJdbcRepository.save(request);

        //then
        assertThat(savedTheme.getName()).isEqualTo(request.getName());
    }

    @DisplayName("모든 테마를 조회할 수 있습니다.")
    @Test
    void findAll() {
        //when
        final List<ReservationTheme> savedThemes = reservationThemeJdbcRepository.findAll();

        //then
        assertThat(savedThemes.size()).isEqualTo(2);
    }

    @DisplayName("테마를 조회할 수 있습니다.")
    @Test
    void findById() {
        //given
        final ReservationTheme request = ReservationThemeFixture.createReservationTheme();
        final ReservationTheme savedTheme = reservationThemeJdbcRepository.save(request);

        //when, then
        assertThat(savedTheme.getName()).isEqualTo(request.getName());
    }

    @DisplayName("테마를 삭제할 수 있습니다.")
    @Test
    void deleteById() {
        //given
        final ReservationTheme request = ReservationThemeFixture.createReservationTheme();
        final ReservationTheme savedTheme = reservationThemeJdbcRepository.save(request);

        //when
        reservationThemeJdbcRepository.deleteById(savedTheme.getId());

        //then
        assertThat(reservationThemeJdbcRepository.existsById(savedTheme.getId())).isFalse();
    }

    @DisplayName("테마가 존재하는지 확인할 수 있습니다.")
    @Test
    void existById() {
        //given
        final ReservationTheme request = ReservationThemeFixture.createReservationTheme();
        final ReservationTheme savedTheme = reservationThemeJdbcRepository.save(request);

        //when
        final boolean isExisted = reservationThemeJdbcRepository.existsById(savedTheme.getId());

        //then
        assertThat(isExisted).isTrue();
    }
}
