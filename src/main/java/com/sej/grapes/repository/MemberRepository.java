package com.sej.grapes.repository;

import com.sej.grapes.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //@Query("select m from Member m where m.email = :email")
    Optional<Member> findMemberByEmail(String email);

}
