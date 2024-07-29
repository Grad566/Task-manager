package hexlet.code.mapper;

import hexlet.code.dto.LabelCreatedDTO;
import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.LabelUpdatedDTO;
import hexlet.code.model.Label;
import org.mapstruct.Mapper;
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
public abstract class LabelMapper {
    public abstract Label map(LabelCreatedDTO dto);
    public abstract LabelDTO map(Label model);
    public abstract void update(LabelUpdatedDTO dto, @MappingTarget Label model);
}
