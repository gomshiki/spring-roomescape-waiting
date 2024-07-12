package roomescape.reservation.dto;

import roomescape.reservation.domain.Waiting;
import roomescape.reservationtheme.dto.ReservationThemeResponseDto;
import roomescape.reservationtime.dto.ReservationTimeResponseDto;

public class ReservationWaitingResponseDto {

    private Long id;
    private String date;
    private ReservationTimeResponseDto reservationTimeResponseDto;
    private ReservationThemeResponseDto reservationThemeResponseDto;
    private String status;

    public ReservationWaitingResponseDto(
            Long id,
            String date,
            ReservationTimeResponseDto reservationTimeResponseDto,
            ReservationThemeResponseDto reservationThemeResponseDto,
            String status
    ) {
        this.id = id;
        this.date = date;
        this.reservationTimeResponseDto = reservationTimeResponseDto;
        this.reservationThemeResponseDto = reservationThemeResponseDto;
        this.status = status;
    }

    public static ReservationWaitingResponseDto from(Waiting savedWaiting) {
        return new ReservationWaitingResponseDto(
                savedWaiting.getId(),
                savedWaiting.getDate(),
                new ReservationTimeResponseDto(
                        savedWaiting.getReservationTime().getId(),
                        savedWaiting.getReservationTime().getStartAt()),
                new ReservationThemeResponseDto(
                        savedWaiting.getReservationTheme().getId(),
                        savedWaiting.getReservationTheme().getName(),
                        savedWaiting.getReservationTheme().getDescription(),
                        savedWaiting.getReservationTheme().getThumbnail()
                ),
                savedWaiting.getStatus()
        );
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public ReservationTimeResponseDto getReservationTimeResponseDto() {
        return reservationTimeResponseDto;
    }

    public ReservationThemeResponseDto getReservationThemeResponseDto() {
        return reservationThemeResponseDto;
    }

    public String getStatus() {
        return status;
    }
}

