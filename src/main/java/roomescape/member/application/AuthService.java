package roomescape.member.application;

import org.springframework.stereotype.Service;
import roomescape.member.domain.Member;
import roomescape.member.dto.LoginMemberRequestDto;
import roomescape.member.dto.MemberResponseDto;
import roomescape.member.dto.TokenResponseDto;
import roomescape.member.infra.AuthorizationException;
import roomescape.member.infra.JwtTokenProvider;
import roomescape.member.infra.MemberJpaRepository;
import roomescape.member.infra.MemberRepository;

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
        final Long id = memberRepository.findIdByEmail(loginMember.getEmail()).get();

        String accessToken = jwtTokenProvider.createToken(String.valueOf(id));
        return new TokenResponseDto(accessToken);
    }

    public void checkMember(final Member member) {
        final boolean existByEmailAndPassword = memberRepository.existsByEmailAndPassword(member.getEmail(), member.getPassword());
        if (!existByEmailAndPassword) {
            throw new AuthorizationException("해당하는 회원 정보가 없습니다.");
        }
    }

    public MemberResponseDto findMemberName(final String token) {
        final boolean isTokenExpired = jwtTokenProvider.validateToken(token);
        if (!isTokenExpired) {
            throw new AuthorizationException("만료된 토큰입니다.");
        }
        final String id = jwtTokenProvider.extractMemberIdFromToken(token);
        final String nameById = memberRepository.findNameById(Long.parseLong(id)).get();
        return new MemberResponseDto(nameById);
    }

    public MemberResponseDto findMember(final String token){
        final boolean isTokenValid = jwtTokenProvider.validateToken(token);
        if (!isTokenValid) {
            throw new AuthorizationException("만료된 토큰입니다.");
        }
        final String id = jwtTokenProvider.extractMemberIdFromToken(token);
        final Member foundMember = memberRepository.findById(Long.parseLong(id)).get();
        return new MemberResponseDto(foundMember.getId(), foundMember.getName(), foundMember.getEmail(), foundMember.getRole());
    }
}
