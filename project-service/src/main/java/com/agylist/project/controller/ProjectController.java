package com.agylist.project.controller;

import com.agylist.project.dto.ProjectRequest;
import com.agylist.project.model.Project;
import com.agylist.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody ProjectRequest request,
                                              @RequestHeader String username) {
        projectService.createNewProject(request, username);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID projectId) {
        val project = projectService.getProjectById(projectId);
        return ResponseEntity.status(200).body(project);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects(@RequestHeader String username) {
        val projects = projectService.getAllProjects(username);
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@RequestBody ProjectRequest request,
                                                 @PathVariable UUID projectId,
                                                 @RequestHeader String username) {
        val project = projectService.updateProject(projectId, request, username);
        return ResponseEntity.ok(project);
    }

}
