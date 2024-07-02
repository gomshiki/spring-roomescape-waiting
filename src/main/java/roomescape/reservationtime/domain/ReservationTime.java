package roomescape.reservationtime.domain;

import jakarta.persistence.*;
import roomescape.reservation.domain.Reservation;

import java.util.List;
import java.util.Objects;

@Entity
public class ReservationTime {

    private final static ReservationTimePolicy RESERVATION_TIME_POLICY = new ReservationTimePolicy();

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startAt;

    @OneToMany(mappedBy = "reservationTime")
    private List<Reservation> reservation;

    public ReservationTime() {
    }

    public ReservationTime(Long id) {
        this.id = id;
    }

    public ReservationTime(String startAt) {
        if (RESERVATION_TIME_POLICY.validateStartAt(startAt)) {
            throw new IllegalArgumentException("예약 시간 형식이 올바르지 않습니다.");
        }
        this.startAt = startAt;
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        if (RESERVATION_TIME_POLICY.validateStartAt(startAt)) {
            throw new IllegalArgumentException("예약 시간 형식이 올바르지 않습니다.");
        }
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
