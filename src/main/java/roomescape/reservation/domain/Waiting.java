package roomescape.reservation.domain;

import jakarta.persistence.*;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtime.domain.ReservationTime;

@Entity
public class Waiting {
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
    private String status;

    protected Waiting() {
    }

    public Waiting(String name, String date, ReservationTime reservationTime, ReservationTheme reservationTheme, String status) {
        this(null, name, date, reservationTime, reservationTheme, status);
    }

    public Waiting(Long id, String name, String date, ReservationTime reservationTime, ReservationTheme reservationTheme, String status) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
        this.reservationTheme = reservationTheme;
        this.status = status;
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
    public String toString() {
        return "Waiting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reservationTime=" + reservationTime +
                ", reservationTheme=" + reservationTheme +
                ", status='" + status + '\'' +
                '}';
    }
}
