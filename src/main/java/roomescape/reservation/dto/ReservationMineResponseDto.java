package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationMineResponseDto {
    @JsonProperty("reservationId")
    private Long reservationId;
    @JsonProperty("date")
    private String date;
    @JsonProperty("status")
    private String status;
    @JsonProperty("time")
    private String timeStartAt;
    @JsonProperty("theme")
    private String themeName;

    public ReservationMineResponseDto(Long reservationId, String date, String status, String timeStartAt, String themeName) {
        this.reservationId = reservationId;
        this.date = date;
        this.status = status;
        this.timeStartAt = timeStartAt;
        this.themeName = themeName;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeStartAt() {
        return timeStartAt;
    }

    public String getThemeName() {
        return themeName;
    }
}
