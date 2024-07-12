package roomescape.reservation.domain;

public class ReservationTimeWithStatus {
    private Long timeId;
    private String startAt;
    private Boolean status;

    public ReservationTimeWithStatus(Long timeId, String startAt, Boolean status) {
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
