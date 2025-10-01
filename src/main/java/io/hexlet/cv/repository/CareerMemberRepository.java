package io.hexlet.cv.repository;

import io.hexlet.cv.model.CareerMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerMemberRepository extends JpaRepository<CareerMember, Long> {
    List<CareerMember> findByUserId(Long id);
}
