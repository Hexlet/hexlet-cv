package io.hexlet.cv.specification;

import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InterviewSpecification {

    public Specification<Interview> build(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return builder.conjunction();
            }

            String processedKeyword = keyword.trim().toLowerCase();
            List<Predicate> conditions = new ArrayList<>();

            Join<Interview, User> speakerJoin = root.join("speaker", JoinType.LEFT);

            conditions.add(builder.like(builder.lower(root.get("title")), "%" + processedKeyword + "%"));
            conditions.add(builder.like(builder.lower(speakerJoin.get("firstName")), "%" + processedKeyword + "%"));
            conditions.add(builder.like(builder.lower(speakerJoin.get("lastName")), "%" + processedKeyword + "%"));

            return builder.or(conditions.toArray(new Predicate[0]));
        };
    }
}
