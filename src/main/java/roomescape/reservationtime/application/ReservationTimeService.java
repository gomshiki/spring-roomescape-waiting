package roomescape.reservationtime.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequestDto;
import roomescape.reservationtime.dto.ReservationTimeResponseDto;
import roomescape.reservationtime.infra.ReservationTimeJpaRepository;
import roomescape.reservationtime.infra.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeJpaRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponseDto> getTimes() {
        final List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(time -> new ReservationTimeResponseDto(time.getId(), time.getStartAt())
                ).toList();
    }

    @Transactional
    public ReservationTimeResponseDto addTime(final ReservationTimeRequestDto reservationTimeRequestDto) {
        final ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDto.getStartAt());
        final ReservationTime savedTime = reservationTimeRepository.save(reservationTime);
        return new ReservationTimeResponseDto(savedTime.getId(), savedTime.getStartAt());
    }

    @Transactional
    public void deleteTime(final Long id) {
        final boolean isExistedTime = reservationTimeRepository.existsById(id);
        if (!isExistedTime) {
            throw new IllegalArgumentException("해당 시간이 존재하지 않습니다.");
        }
        reservationTimeRepository.deleteById(id);
    }
}
