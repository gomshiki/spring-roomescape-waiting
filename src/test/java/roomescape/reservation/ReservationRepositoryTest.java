package roomescape.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import roomescape.globalfixture.entity.ReservationFixture;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.infra.ReservationJdbcRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestPropertySource(locations = "classpath:test-application.yml")
@Sql(scripts = "/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationRepositoryTest {

    private ReservationJdbcRepository reservationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationJdbcRepository(jdbcTemplate);
    }

    @DisplayName("전체 예약을 조회합니다.")
    @Test
    void findAll() {
        // when
        final List<Reservation> actual = reservationRepository.findAllWithDetails();

        // then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("예약을 저장합니다.")
    @Test
    void save() {
        // given
        final Reservation request = ReservationFixture.createReservation();

        // when
        final Reservation actual = reservationRepository.save(request);

        // then
        assertThat(actual.getDate()).isEqualTo(request.getDate());
        assertThat(actual.getName()).isEqualTo(request.getName());
    }

    @DisplayName("예약 삭제을 삭제합니다.")
    @Test
    void deleteById() {
        // given
        final Reservation request = ReservationFixture.createReservation();
        final Reservation savedReservation = reservationRepository.save(request);

        // when
        reservationRepository.deleteById(savedReservation.getId());

        // then
        assertThat(reservationRepository.existsById(savedReservation.getId())).isFalse();
    }
}
