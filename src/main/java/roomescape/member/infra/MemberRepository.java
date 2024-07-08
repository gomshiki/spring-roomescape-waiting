package roomescape.member.infra;

import roomescape.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<String> findNameById(long id);

    Optional<Member> findById(long id);

    Optional<Long> findIdByEmailAndPassword(String email, String password);

    boolean existsByEmailAndPassword(String email, String password);

    List<Member> findAll();
}
