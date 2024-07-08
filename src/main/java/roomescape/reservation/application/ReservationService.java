package roomescape.reservation.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationMineResponseDto;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.infra.ReservationJpaRepository;
import roomescape.reservation.infra.ReservationRepository;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeResponseDto;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationJpaRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> findAll() {
        final List<Reservation> reservations = reservationRepository.findAllWithDetails();
        return reservations.stream().map(ReservationResponseDto::from).toList();
    }

    public ReservationResponseDto save(ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = ReservationRequestDto.from(reservationRequestDto);

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

    public List<ReservationTimeResponseDto> findAvailableTimes(String date, Long themeId) {
        final List<ReservationTime> availableReservationTimes =
                reservationRepository.getAvailableReservationTimes(date, themeId);

        return availableReservationTimes.stream()
                .map(reservationTime -> new ReservationTimeResponseDto(
                        reservationTime.getId(),
                        reservationTime.getStartAt()
                )).toList();
    }

    public List<ReservationMineResponseDto> findAllReservationByName(String name) {
        List<Reservation> foundReservations = reservationRepository.findByNameWithDetails(name);
        return foundReservations.stream().map(
                reservation -> new ReservationMineResponseDto(
                        reservation.getId(),
                        reservation.getDate(),
                        reservation.getStatus(),
                        reservation.getReservationTime().getStartAt(),
                        reservation.getReservationTheme().getName()
                )
        ).toList();
    }
}
