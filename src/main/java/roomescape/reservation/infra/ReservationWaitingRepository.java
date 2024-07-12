package roomescape.reservation.infra;

import org.springframework.data.repository.CrudRepository;
import roomescape.reservation.domain.Waiting;

public interface ReservationWaitingRepository extends CrudRepository<Waiting, Long> {
    Waiting save(Waiting waiting);
}
