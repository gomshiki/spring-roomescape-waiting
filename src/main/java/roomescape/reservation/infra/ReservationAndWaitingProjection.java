package roomescape.reservation.infra;

public interface ReservationAndWaitingProjection {
    Long getId();
    String getName();
    String getDate();
    String getStartAt();
    String getThemeName();
    String getStatus();
}
