package io.hexlet.cv.controller.admin.marketing;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.EnumService;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@Getter
public abstract class BaseMarketingController {

    @Autowired
    private Inertia inertia;

    @Autowired
    private EnumService enumService;

    protected static final String ACTIVE_MAIN_SECTION = "activeMainSection";
    protected static final String ACTIVE_SUB_SECTION = "activeSubSection";
    protected static final String MAIN_SECTION = "marketing";

    protected Map<String, Object> createBaseProps(String subSection) {
        return Map.of(
                ACTIVE_MAIN_SECTION, MAIN_SECTION,
                ACTIVE_SUB_SECTION, subSection
        );
    }

    protected Map<String, Object> createPaginationMap(Page<?> page, Pageable pageable) {
        return Map.of(
                "currentPage", page.getNumber(),
                "totalPages", page.getTotalPages(),
                "totalElements", page.getTotalElements(),
                "pageSize", pageable.getPageSize()
        );
    }

    protected Map<String, Object> createTeamFormProps(String subSection) {
        Map<String, Object> props = new HashMap<>(createBaseProps(subSection));
        props.putAll(enumService.getTeamEnums());
        return props;
    }
}
