package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationTimeWithStatusDto {
    @JsonProperty("id")
    private Long timeId;
    @JsonProperty("startAt")
    private String startAt;
    @JsonProperty("status")
    private Boolean status;

    public ReservationTimeWithStatusDto() {
    }

    public ReservationTimeWithStatusDto(Long timeId, String startAt, Boolean status) {
        this.timeId = timeId;
        this.startAt = startAt;
        this.status = status;
    }

    public Long getTimeId() {
        return timeId;
    }

    public String getStartAt() {
        return startAt;
    }

    public Boolean getStatus() {
        return status;
    }
}
