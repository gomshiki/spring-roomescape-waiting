package roomescape.member.application;

import org.springframework.stereotype.Service;
import roomescape.member.domain.Member;
import roomescape.member.dto.LoginMemberRequestDto;
import roomescape.member.dto.MemberResponseDto;
import roomescape.member.dto.TokenResponseDto;
import roomescape.member.infra.*;


@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberJpaRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    public TokenResponseDto createToken(final LoginMemberRequestDto loginMemberRequestDto) {
        final Member loginMember = new Member(loginMemberRequestDto.getEmail(), loginMemberRequestDto.getPassword());
        checkMember(loginMember);
        final Long foundId = memberRepository
                .findIdByEmailAndPassword(loginMember.getEmail(), loginMember.getPassword())
                .orElseThrow(MemberNotFoundException::new);

        String accessToken = jwtTokenProvider.createToken(String.valueOf(foundId));
        return new TokenResponseDto(accessToken);
    }

    public void checkMember(final Member member) {
        final boolean existByEmailAndPassword = memberRepository.existsByEmailAndPassword(
                member.getEmail(), member.getPassword());
        if (!existByEmailAndPassword) {
            throw new MemberNotFoundException();
        }
    }

    public MemberResponseDto findMemberName(final String token) {
        final boolean isTokenExpired = jwtTokenProvider.validateToken(token);
        if (!isTokenExpired) {
            throw new AuthorizationException();
        }
        final String id = jwtTokenProvider.extractMemberIdFromToken(token);
        final String nameById = memberRepository.findNameById(Long.parseLong(id))
                .orElseThrow(MemberNotFoundException::new);

        return new MemberResponseDto(nameById);
    }

    public MemberResponseDto findMember(final String token) {
        final boolean isTokenValid = jwtTokenProvider.validateToken(token);
        if (!isTokenValid) {
            throw new AuthorizationException();
        }
        final String id = jwtTokenProvider.extractMemberIdFromToken(token);
        final Member foundMember = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(MemberNotFoundException::new);

        return new MemberResponseDto(foundMember.getId(), foundMember.getName(), foundMember.getEmail(), foundMember.getRole());
    }
}
