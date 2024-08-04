package hexlet.code.mapper;


import hexlet.code.dto.taskDTO.TaskCreatedDTO;
import hexlet.code.dto.taskDTO.TaskDTO;
import hexlet.code.dto.taskDTO.TaskUpdatedDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus", source = "slug")
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds")
    public abstract Task map(TaskCreatedDTO dto);

    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "content", source = "description")
    @Mapping(target = "slug", source = "taskStatus.slug")
    @Mapping(target = "taskLabelIds", source = "labels")
    public abstract TaskDTO map(Task model);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus", source = "slug")
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds")
    public abstract void updated(TaskUpdatedDTO dto, @MappingTarget Task model);

    public TaskStatus toTaskStatus(String statusSlug) {
        return taskStatusRepository.findBySlug(statusSlug)
                .orElseThrow();
    }

    public List<Label> toLabels(List<Long> ids) {
        return new ArrayList<>(labelRepository.findAllById(ids));
    }

    public List<Long> toLabelIdsList(List<Label> labels) {
        if (labels == null) {
            return new ArrayList<Long>();
        } else {
            return labels.stream()
                    .map(Label::getId)
                    .toList();
        }
    }
}
