package hexlet.code.app.service;

import hexlet.code.app.dto.TaskCreatedDTO;
import hexlet.code.app.dto.TaskDTO;
import hexlet.code.app.dto.TaskUpdatedDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.repository.TaskRepository;
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

    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream().map(taskMapper::map).toList();
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

    @Transactional
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
