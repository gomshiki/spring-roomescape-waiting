package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import roomescape.reservationtheme.dto.ReservationThemeRequestDto;

public class ReservationWaitingRequestDto {

    private Long id;
    private String name;

    @NotBlank(message = "예약일자를 입력해주세요")
    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private TimeDto timeDto;

    @JsonProperty("theme")
    private ReservationThemeRequestDto reservationThemeRequestDto;

    private String status;

    public ReservationWaitingRequestDto() {
    }

    public ReservationWaitingRequestDto(String name, String date, TimeDto timeDto, ReservationThemeRequestDto reservationThemeRequestDto, String status) {
        this.name = name;
        this.date = date;
        this.timeDto = timeDto;
        this.reservationThemeRequestDto = reservationThemeRequestDto;
        this.status = status;
    }

    public ReservationWaitingRequestDto(
            Long id, String name, String date, TimeDto timeDto, ReservationThemeRequestDto reservationThemeRequestDto, String status
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeDto = timeDto;
        this.reservationThemeRequestDto = reservationThemeRequestDto;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public @NotBlank(message = "예약일자를 입력해주세요") String getDate() {
        return date;
    }

    public TimeDto getTimeDto() {
        return timeDto;
    }

    public ReservationThemeRequestDto getReservationThemeRequestDto() {
        return reservationThemeRequestDto;
    }

    public void assignName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReservationWaitingRequestDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", timeDto=" + timeDto +
                ", reservationThemeRequestDto=" + reservationThemeRequestDto +
                ", status='" + status + '\'' +
                '}';
    }
}
