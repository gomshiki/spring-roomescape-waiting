package roomescape.reservation.application;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.infra.ReservationJpaRepository;
import roomescape.reservation.infra.ReservationRepository;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeResponseDto;

import java.util.List;

import static roomescape.reservation.dto.ReservationResponseDto.reservationToDto;

@Service
public class ReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    private final ReservationRepository reservationRepository;
    private final EntityManager entityManager;

    public ReservationService(ReservationJpaRepository reservationRepository, EntityManager entityManager) {
        this.reservationRepository = reservationRepository;
        this.entityManager = entityManager;
    }

    public List<ReservationResponseDto> findAll() {
        final List<Reservation> reservations = reservationRepository.findAllWithDetails();
        return reservations.stream()
                .map(reservation -> reservationToDto(reservation)
                ).toList();
    }

    public ReservationResponseDto save(final ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = new Reservation.Builder()
                .name(reservationRequestDto.getName())
                .date(reservationRequestDto.getDate())
                .reservationTime(new ReservationTime(
                    reservationRequestDto.getTimeDto().getTimeId()))
                .reservationTheme(new ReservationTheme(
                    reservationRequestDto.getReservationThemeRequestDto().getThemeId()))
                .build();

        final Reservation savedReservation = reservationRepository.save(reservation);

        entityManager.clear();

        final Reservation foundAllReservation = reservationRepository.findByIdWithDetails(savedReservation.getId()).get();

        return reservationToDto(foundAllReservation);
    }


    @Transactional
    public void delete(final Long id) {
        final boolean isExistedReservation = reservationRepository.existsById(id);
        if (!isExistedReservation) {
            throw new IllegalArgumentException("해당 예약이 존재하지 않습니다.");
        }
        reservationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponseDto> findAvailableTimes(final String date, final Long themeId) {
        final List<ReservationTime> availableReservationTimes = reservationRepository.getAvailableReservationTimes(date, themeId);
        return availableReservationTimes.stream()
                .map(reservationTime -> new ReservationTimeResponseDto(
                    reservationTime.getId(),
                    reservationTime.getStartAt()
                )).toList();
    }
}
