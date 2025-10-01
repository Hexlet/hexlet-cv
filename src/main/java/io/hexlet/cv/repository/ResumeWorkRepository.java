package io.hexlet.cv.repository;

import io.hexlet.cv.model.ResumeWork;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeWorkRepository extends JpaRepository<ResumeWork, Long> {
    @Query("SELECT w FROM ResumeWork w JOIN w.resume r WHERE r.user.id = :userId")
    List<ResumeWork> findByUserId(@Param("userId") Long userId);
}
