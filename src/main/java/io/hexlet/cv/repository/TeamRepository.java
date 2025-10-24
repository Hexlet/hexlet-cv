package io.hexlet.cv.repository;

import io.hexlet.cv.model.marketing.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByOrderByCreatedAtDesc();

    List<Team> findByShowOnHomepageTrueOrderByDisplayOrderAsc();

    Optional<Team> findByIdAndIsPublishedTrue(Long id);
}
