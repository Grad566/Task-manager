package hexlet.code.app.controller;

import hexlet.code.app.dto.TaskStatusCreatedDTO;
import hexlet.code.app.dto.TaskStatusDTO;
import hexlet.code.app.dto.TaskStatusUpdatedDTO;
import hexlet.code.app.service.TaskStatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/task_statuses")
public class TaskStatusController {
    @Autowired
    private TaskStatusService service;

    @GetMapping(path = "/{id}")
    public TaskStatusDTO show(@PathVariable Long id) {
        return service.show(id);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<TaskStatusDTO>> index() {
        var statuses = service.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(statuses.size()))
                .body(statuses);
    }

    @PostMapping(path = "")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO create(@Valid @RequestBody TaskStatusCreatedDTO data) {
        return service.create(data);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("isAuthenticated()")
    public TaskStatusDTO update(@Valid @RequestBody TaskStatusUpdatedDTO data, @PathVariable Long id) {
        return service.update(data, id);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        service.destroy(id);
    }

}
