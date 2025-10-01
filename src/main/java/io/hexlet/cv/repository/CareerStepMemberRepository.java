package io.hexlet.cv.repository;


import io.hexlet.cv.model.CareerStepMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerStepMemberRepository extends JpaRepository<CareerStepMember, Long> {
    int countByCareerMemberIdAndState(Long careerMemberId, String state);

    List<CareerStepMember> findByCareerMemberIdAndState(Long careerMemberId, String state);

    List<CareerStepMember> findByCareerMemberIdAndStateOrderByUpdatedAtDesc(Long careerMemberId, String state);

}
