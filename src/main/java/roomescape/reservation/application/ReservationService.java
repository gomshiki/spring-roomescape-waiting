package roomescape.reservation.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationMineResponseDto;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.domain.ReservationTimeWithStatus;
import roomescape.reservation.dto.ReservationTimeWithStatusDto;
import roomescape.reservation.infra.ReservationAndWaitingProjection;
import roomescape.reservation.infra.ReservationJpaRepository;
import roomescape.reservation.infra.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private String currentStatus;

    public void setCurrentStatus(String status) {
        this.currentStatus = status;
    }

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationJpaRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> findAll() {
        final List<Reservation> reservations = reservationRepository.findAllWithDetails();
        return reservations.stream().map(ReservationResponseDto::from).toList();
    }

    public ReservationResponseDto save(ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = ReservationRequestDto.of(reservationRequestDto, currentStatus);

        final Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponseDto.from(savedReservation);
    }


    @Transactional
    public void delete(final Long id) {
        final boolean isExistedReservation = reservationRepository.existsById(id);
        if (!isExistedReservation) {
            throw new IllegalArgumentException("해당 예약이 존재하지 않습니다.");
        }
        reservationRepository.deleteById(id);
    }

    public List<ReservationTimeWithStatusDto> findAvailableTimes(String date, Long themeId) {
        final List<ReservationTimeWithStatus> availableReservationTimes =
                reservationRepository.findReservationTimesWithStatus(date, themeId);

        return availableReservationTimes.stream()
                .map(reservationTimeWithStatus ->
                        new ReservationTimeWithStatusDto(
                                reservationTimeWithStatus.getTimeId(),
                                reservationTimeWithStatus.getStartAt(),
                                reservationTimeWithStatus.getStatus()
                        )
                ).toList();
    }

    public List<ReservationMineResponseDto> findAllReservationByName(String name) {
        List<ReservationAndWaitingProjection> foundReservations = reservationRepository.findByNameWithDetails(name);

        return foundReservations.stream().map(ReservationMineResponseDto::from).toList();

    }
}
