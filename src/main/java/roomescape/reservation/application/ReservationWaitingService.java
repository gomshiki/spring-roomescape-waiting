package roomescape.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Waiting;
import roomescape.reservation.dto.ReservationWaitingRequestDto;
import roomescape.reservation.dto.ReservationWaitingResponseDto;
import roomescape.reservation.infra.ReservationWaitingRepository;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtime.domain.ReservationTime;

@Service
public class ReservationWaitingService {

    private String currentStatus;

    public void setCurrentStatus(String status) {
        this.currentStatus = status;
    }

    private final ReservationWaitingRepository reservationWaitingRepository;

    public ReservationWaitingService(ReservationWaitingRepository reservationWaitingRepository) {
        this.reservationWaitingRepository = reservationWaitingRepository;
    }

    public ReservationWaitingResponseDto save(ReservationWaitingRequestDto reservationWaitingRequestDto) {
        Waiting waiting = new Waiting(
                reservationWaitingRequestDto.getName(),
                reservationWaitingRequestDto.getDate(),
                new ReservationTime(reservationWaitingRequestDto.getTimeDto().getTimeId()),
                new ReservationTheme(reservationWaitingRequestDto.getReservationThemeRequestDto().getThemeId()),
                currentStatus
        );
        Waiting savedWaiting = reservationWaitingRepository.save(waiting);
        return ReservationWaitingResponseDto.from(savedWaiting);
    }
}
