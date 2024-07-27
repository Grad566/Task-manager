package hexlet.code.app.service;

import hexlet.code.app.dto.TaskCreatedDTO;
import hexlet.code.app.dto.TaskDTO;
import hexlet.code.app.dto.TaskUpdatedDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDTO> getAll(Map<String, String> filters) {
        List<Task> tasks = taskRepository.findAll();
        if (filters == null) {
            return tasks.stream().map(taskMapper::map).toList();
        } else {
            return filterTasks(filters, tasks).stream().map(taskMapper::map).toList();
        }
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

    private List<Task> filterTasks(Map<String, String> filters, List<Task> tasks) {
        var predicates = new HashMap<String, BiPredicate<Task, Object>>();
        predicates.put("titleCont", (task, str) -> task.getName().contains((String) str));
        predicates.put("assigneeId", (task, id) -> task.getId().equals(Long.parseLong((String) id)));
        predicates.put("status", (task, slug) -> task.getTaskStatus().getSlug().equals((String) slug));
        predicates.put("labelId", (task, labelId) -> task.getLabels().stream()
                .anyMatch(label -> label.getId().equals(Long.parseLong((String) labelId))));

        return tasks.stream()
                .filter(t -> filters.entrySet().stream()
                        .allMatch(entry -> {
                            BiPredicate<Task, Object> predicate = predicates.get(entry.getKey());
                            if (predicate != null && entry.getValue() != null) {
                                return predicate.test(t, entry.getValue());
                            } else {
                                return true;
                            }
                        }))
                .toList();
    }
}
