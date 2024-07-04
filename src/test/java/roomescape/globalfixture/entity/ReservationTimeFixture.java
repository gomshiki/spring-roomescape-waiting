package roomescape.globalfixture.entity;

import roomescape.reservationtime.domain.ReservationTime;

public class ReservationTimeFixture {

    public static ReservationTime createReservationTime(){
        return new ReservationTime("18:00");
    }
}
