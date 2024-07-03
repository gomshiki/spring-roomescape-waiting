package roomescape;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.infra.ReservationRepository;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtime.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:test-application.yml")
@Sql(scripts = "/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationJpaTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("엔티티 저장 후 entityManager 를 clear 해야 연관관계 엔티티의 필드값을 조회한다.")
    @Test
    void saveAndFindReservation() {

        Reservation request = new Reservation("김준성", "2025-12-25", new ReservationTime(1L), new ReservationTheme(2L));
        Reservation savedReservation = reservationRepository.save(request);
        Reservation contextReservation = entityManager.find(Reservation.class, savedReservation.getId());
        assertThat(savedReservation.getId()).isEqualTo(contextReservation.getId());

        entityManager.clear();

        Reservation updateReservation = reservationRepository.findByIdWithDetails(contextReservation.getId()).get();
        assertThat(updateReservation.getReservationTheme().getName()).isNotNull();
    }
}
