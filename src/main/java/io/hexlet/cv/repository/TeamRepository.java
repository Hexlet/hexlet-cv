package io.hexlet.cv.repository;

import io.hexlet.cv.model.admin.marketing.Team;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Team> findByShowOnHomepageTrue(Sort sort);
}
