package com.flab.infrun.member.infrastructure.persistence;

import com.flab.infrun.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Member save(final Member member);

    boolean existsByEmail(final String email);

    boolean existsByNickname(final String nickname);

    Optional<Member> findByEmail(final String email);
}
