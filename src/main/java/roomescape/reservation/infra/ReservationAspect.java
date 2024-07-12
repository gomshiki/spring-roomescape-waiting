package roomescape.reservation.infra;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.application.ReservationWaitingService;

@Aspect
@Component
public class ReservationAspect {
    private static final Logger log = LoggerFactory.getLogger(ReservationAspect.class);

    private final ReservationService reservationService;
    private final ReservationWaitingService reservationWaitingService;

    public ReservationAspect(ReservationService reservationService, ReservationWaitingService reservationWaitingService) {
        this.reservationService = reservationService;
        this.reservationWaitingService = reservationWaitingService;
    }

    @Before("@annotation(reservationStatus)")
    public void setReservationStatus(ReservationStatus reservationStatus) {
        reservationService.setCurrentStatus(reservationStatus.value());
    }

    @Before("@annotation(waitingStatus)")
    public void setWaitingStatus(WaitingStatus waitingStatus) {
        reservationWaitingService.setCurrentStatus(waitingStatus.value());
        log.info("Waiting status set to: {}", waitingStatus.value());
    }
}
