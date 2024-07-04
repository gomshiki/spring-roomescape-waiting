package roomescape.reservationtime.infra;

import org.springframework.data.repository.CrudRepository;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeJpaRepository extends CrudRepository<ReservationTime, Long>, ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<ReservationTime> findById(Long id);

}
