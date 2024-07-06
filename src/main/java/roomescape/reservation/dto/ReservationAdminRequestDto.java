package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationAdminRequestDto {
    @JsonProperty("memberId")
    private Long memberId;

    @JsonProperty("themeId")
    private Long themeId;

    @JsonProperty("timeId")
    private Long timeId;

    @JsonProperty("date")
    private String date;

    public ReservationAdminRequestDto(Long memberId, Long themeId, Long timeId, String date) {
        this.memberId = memberId;
        this.themeId = themeId;
        this.timeId = timeId;
        this.date = date;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public Long getTimeId() {
        return timeId;
    }

    public String getDate() {
        return date;
    }
}
