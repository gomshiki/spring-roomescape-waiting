package roomescape.globalfixture.dto;

import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.TimeDto;
import roomescape.reservationtheme.dto.ReservationThemeRequestDto;

public class ReservationDtoFixture {

    public static ReservationRequestDto createReservationDto(){
        final ReservationThemeRequestDto reservationThemeDto = ReservationThemeDtoFixture.createReservationThemeDto();
        final TimeDto timeDto = TimeDtoFixture.timeDtoCreate(1L);
        return new ReservationRequestDto("제이슨", "2025-08-15", timeDto, reservationThemeDto);
    }
}
