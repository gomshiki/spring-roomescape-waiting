package roomescape.member.application;

import org.springframework.stereotype.Service;
import roomescape.member.domain.Member;
import roomescape.member.dto.MemberResponseDto;
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
        return foundMembers.stream().map(
                member -> new MemberResponseDto(member.getId(), member.getName())
        ).toList();


    }
}
