package hexlet.code.service;

import hexlet.code.dto.taskStatusDTO.TaskStatusCreatedDTO;
import hexlet.code.dto.taskStatusDTO.TaskStatusDTO;
import hexlet.code.dto.taskStatusDTO.TaskStatusUpdatedDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class TaskStatusService {
    private final TaskStatusMapper mapper;
    private final TaskStatusRepository repository;

    public TaskStatusDTO show(Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow();
        return mapper.map(taskStatus);
    }

    public List<TaskStatusDTO> getAll() {
        var taskStatuses = repository.findAll();
        return taskStatuses.stream().map(mapper::map).toList();
    }

    @Transactional
    public TaskStatusDTO create(TaskStatusCreatedDTO data) {
        var taskStatus = mapper.map(data);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    @Transactional()
    public TaskStatusDTO update(TaskStatusUpdatedDTO data, Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow();
        mapper.update(data, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public void destroy(Long id) {
        repository.deleteById(id);
    }
}
