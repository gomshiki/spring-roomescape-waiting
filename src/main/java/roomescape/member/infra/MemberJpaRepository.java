package roomescape.member.infra;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import roomescape.member.domain.Member;

import java.util.Optional;

public interface MemberJpaRepository extends CrudRepository<Member, Long>, MemberRepository {
    @Query("SELECT m.name FROM Member m WHERE m.id = :id")
    Optional<String> findNameById(@Param("id") long id);

    Optional<Member> findById(long id);

    @Query("SELECT m.id FROM Member m WHERE m.email = :email AND m.password = :password")
    Optional<Long> findIdByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    boolean existsByEmailAndPassword(String email, String password);
}
