package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeDto {

    @JsonProperty("id")
    private Long timeId;
    @JsonProperty("start_at")
    private String startAt;

    public TimeDto(Long timeId) {
        this.timeId = timeId;
    }

    public TimeDto(Long timeId, String startAt) {
        this.timeId = timeId;
        this.startAt = startAt;
    }

    public Long getTimeId() {
        return timeId;
    }

    public String getStartAt() {
        return startAt;
    }
}
