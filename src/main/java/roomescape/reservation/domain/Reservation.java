package roomescape.reservation.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.Objects;

@Entity
public class Reservation {
    private final static ReservationPolicy RESERVATION_POLICY = new ReservationPolicy();

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private ReservationTime reservationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private ReservationTheme reservationTheme;

    @ColumnDefault("'예약'") // 작은따옴표로 감싸서 문자열로 인식하도록 함
    @Column(name = "status", nullable = false)
    private String status;

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = "예약";
        }
    }

    public Reservation(String name, String date, ReservationTime reservationTime, ReservationTheme reservationTheme) {
        this(null, name, date, reservationTime, reservationTheme,null);
    }

    public Reservation(
            Long id, String name, String date, ReservationTime reservationTime,
            ReservationTheme reservationTheme, String status
    )
    {
        this.id = id;
        if (RESERVATION_POLICY.validateName(name)) {
            throw new IllegalArgumentException("예약자 이름에 특수문자가 포함되어 있습니다.");
        }
        this.name = name;

        if (RESERVATION_POLICY.validateDate(date)) {
            throw new IllegalArgumentException("예약 날짜 형식이 올바르지 않습니다.");
        }
        this.date = date;
        this.reservationTime = reservationTime;
        this.reservationTheme = reservationTheme;
        this.status = status;
    }

    public Reservation() {
    }

    public static class Builder {
        private Long id;
        private String name;
        private String date;
        private ReservationTime reservationTime;
        private ReservationTheme reservationTheme;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder reservationTime(ReservationTime reservationTime) {
            this.reservationTime = reservationTime;
            return this;
        }

        public Builder reservationTheme(ReservationTheme reservationTheme) {
            this.reservationTheme = reservationTheme;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, name, date, reservationTime, reservationTheme, null);
        }
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public ReservationTheme getReservationTheme() {
        return reservationTheme;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                ", reservationTheme='" + reservationTheme + '\'' +
                '}';
    }
}
