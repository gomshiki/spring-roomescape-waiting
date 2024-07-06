package roomescape.member.application;

import org.springframework.stereotype.Service;
import roomescape.member.domain.Member;
import roomescape.member.dto.MemberResponseDto;
import roomescape.member.infra.MemberNotFoundException;
import roomescape.member.infra.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponseDto> findAll() {
        List<Member> foundMembers = memberRepository.findAll();
        return foundMembers.stream().map(MemberResponseDto::from).toList();
    }

    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new MemberNotFoundException("해당 회원 정보가 없습니다.")
        );
        return MemberResponseDto.from(member);
    }
}
