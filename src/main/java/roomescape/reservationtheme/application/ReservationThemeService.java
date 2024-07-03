package roomescape.reservationtheme.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationtheme.domain.ReservationTheme;
import roomescape.reservationtheme.dto.ReservationThemeRequestDto;
import roomescape.reservationtheme.dto.ReservationThemeResponseDto;
import roomescape.reservationtheme.infra.ReservationThemeJpaRepository;
import roomescape.reservationtheme.infra.ReservationThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationThemeService {

    private final ReservationThemeRepository reservationThemeRepository;

    public ReservationThemeService(ReservationThemeJpaRepository reservationThemeRepository) {
        this.reservationThemeRepository = reservationThemeRepository;
    }

    public List<ReservationThemeResponseDto> getThemes() {
        final List<ReservationTheme> themes = reservationThemeRepository.findAll();
        return themes.stream()
                .map(theme -> new ReservationThemeResponseDto(
                        theme.getId(), theme.getName(),
                        theme.getDescription(), theme.getThumbnail())
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationThemeResponseDto createTheme(final ReservationThemeRequestDto requestDto) {
        final ReservationTheme theme = new ReservationTheme.Builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .thumbnail(requestDto.getThumbnail())
                .build();

        final ReservationTheme savedTheme = reservationThemeRepository.save(theme);

        return new ReservationThemeResponseDto(
                savedTheme.getId(), savedTheme.getName(),
                savedTheme.getDescription(), savedTheme.getThumbnail()
        );
    }

    @Transactional
    public void deleteTheme(final Long id) {
        final boolean isExistedTheme = reservationThemeRepository.existsById(id);
        if (!isExistedTheme) {
            throw new IllegalArgumentException("해당 테마가 존재하지 않습니다.");
        }
        reservationThemeRepository.deleteById(id);
    }
}
