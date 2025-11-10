package io.hexlet.cv.repository;

import io.hexlet.cv.model.ResumeEducation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeEducationRepository extends JpaRepository<ResumeEducation, Integer> {

    @Query("SELECT e FROM ResumeEducation e JOIN e.resume r WHERE r.user.id = :userId")
    List<ResumeEducation> findByUserId(@Param("userId") Long userId);

}
