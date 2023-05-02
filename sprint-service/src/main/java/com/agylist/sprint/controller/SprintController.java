package com.agylist.sprint.controller;

import com.agylist.sprint.dto.SprintChangeRequest;
import com.agylist.sprint.dto.SprintRequest;
import com.agylist.sprint.model.Sprint;
import com.agylist.sprint.service.SprintService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sprint")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @PostMapping
    public ResponseEntity<Sprint> createNewSprint(@RequestBody SprintRequest request,
                                                  @RequestHeader String username) {
        val sprint = sprintService.createNewSprint(request, username);
        return ResponseEntity.status(201).body(sprint);
    }

    @PostMapping("/{sprintId}/start")
    public ResponseEntity<Void> startSprint(@PathVariable UUID sprintId,
                                            @RequestHeader String username) {
        sprintService.startSprint(sprintId, username);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/{sprintId}/close")
    public ResponseEntity<Void> closeSprint(@PathVariable UUID sprintId,
                                            @RequestHeader String username) {
        sprintService.closeSprint(sprintId, username);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{sprintId}")
    public ResponseEntity<Sprint> getSprintById(@PathVariable UUID sprintId,
                                                @RequestHeader String username) {
        val sprint = sprintService.getSprintById(sprintId, username);
        return ResponseEntity.ok(sprint);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Sprint>> getAllByProjectId(@PathVariable UUID projectId,
                                                          @RequestHeader String username) {
        val sprints = sprintService.getAllSprintsByProjectId(projectId, username);
        return ResponseEntity.ok(sprints);
    }

    @PutMapping("/{sprintId}")
    public ResponseEntity<Sprint> updateProject(@PathVariable UUID sprintId,
                                                @RequestBody SprintChangeRequest request,
                                                @RequestHeader String username) {
        val updatedSprint = sprintService.updateProject(sprintId, request, username);
        return ResponseEntity.ok(updatedSprint);
    }

}
