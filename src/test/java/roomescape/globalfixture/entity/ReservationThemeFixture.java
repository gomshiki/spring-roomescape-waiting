package roomescape.globalfixture.entity;

import roomescape.reservationtheme.domain.ReservationTheme;

public class ReservationThemeFixture {

    public static ReservationTheme createReservationTheme(){
        return new ReservationTheme("테마1", "설명1", "썸네일1");
    }
}
