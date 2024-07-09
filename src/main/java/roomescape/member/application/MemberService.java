package roomescape.member.application;

import org.springframework.stereotype.Service;
import roomescape.member.domain.Member;
import roomescape.member.dto.MemberResponseDto;
import roomescape.member.infra.MemberNotFoundException;
import roomescape.member.infra.MemberRepository;

import java.util.List;

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
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return MemberResponseDto.from(member);
    }
}
