package io.hexlet.cv.mapper;


import io.hexlet.cv.dto.marketing.TeamCreateDTO;
import io.hexlet.cv.dto.marketing.TeamDTO;
import io.hexlet.cv.dto.marketing.TeamUpdateDTO;
import io.hexlet.cv.model.marketing.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TeamMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Team map(TeamCreateDTO dto);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "siteRole", target = "siteRole")
    @Mapping(source = "systemRole", target = "systemRole")
    @Mapping(source = "avatarUrl", target = "avatarUrl")
    @Mapping(source = "isPublished", target = "isPublished")
    @Mapping(source = "showOnHomepage", target = "showOnHomepage")
    @Mapping(source = "displayOrder", target = "displayOrder")
    @Mapping(source = "publishedAt", target = "publishedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    public abstract TeamDTO map(Team model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Team map(TeamDTO model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract void update(TeamUpdateDTO dto, @MappingTarget Team model);
}
