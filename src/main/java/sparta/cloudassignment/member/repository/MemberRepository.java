package sparta.cloudassignment.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.cloudassignment.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
