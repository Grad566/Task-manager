package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusCreatedDTO;
import hexlet.code.app.dto.TaskStatusDTO;
import hexlet.code.app.dto.TaskStatusUpdatedDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusMapper mapper;

    @Autowired
    private TaskStatusRepository repository;

    public TaskStatusDTO show(Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id " + id + " not found!"));
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

    @Transactional
    public TaskStatusDTO update(TaskStatusUpdatedDTO data, Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id " + id + " not found!"));
        mapper.update(data, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public void destroy(Long id) {
        repository.deleteById(id);
    }
}
