package hexlet.code.service;

import hexlet.code.dto.TaskCreatedDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamDTO;
import hexlet.code.dto.TaskUpdatedDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskSpecification taskSpecification;

    public List<TaskDTO> getAll(TaskParamDTO params) {
        var spec = taskSpecification.build(params);
        return taskRepository.findAll(spec).stream().map(taskMapper::map).toList();
    }

    public TaskDTO show(Long id) {
        var task =  taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with " + id + " not found"));
        return taskMapper.map(task);
    }

    @Transactional
    public TaskDTO create(TaskCreatedDTO data) {
        var task = taskMapper.map(data);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

//    @Transactional
    public TaskDTO updated(TaskUpdatedDTO data, Long id) {
        var task =  taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with " + id + " not found"));
        taskMapper.updated(data, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public void destroy(Long id) {
        taskRepository.deleteById(id);
    }
}
