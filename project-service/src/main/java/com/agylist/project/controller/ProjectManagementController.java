package com.agylist.project.controller;

import com.agylist.project.model.ProjectUser;
import com.agylist.project.model.Role;
import com.agylist.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/project/management")
@RequiredArgsConstructor
public class ProjectManagementController {

    private final ProjectService projectService;

    @PostMapping("/{projectId}/{username}")
    public ResponseEntity<Void> addNewUserToProject(@PathVariable UUID projectId,
                                                    @PathVariable String username,
                                                    @RequestHeader("username") String actionUsername) {
        projectService.addNewUserToProject(projectId, username, actionUsername);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{projectId}/{username}")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable UUID projectId,
                                                      @PathVariable String username,
                                                      @RequestHeader("username") String actionUsername) {
        projectService.removeUserFromProject(projectId, username, actionUsername);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{projectId}/{username}")
    public ResponseEntity<Void> changeRoleForUser(@PathVariable UUID projectId,
                                                  @PathVariable String username,
                                                  @RequestParam Role role,
                                                  @RequestHeader("username") String actionUsername) {
        projectService.changeUserRole(projectId, username, role, actionUsername);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{projectId}/close")
    public ResponseEntity<Void> closeProject(@PathVariable UUID projectId, @RequestHeader String username) {
        projectService.changeProjectStatus(projectId, username, false);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{projectId}/reopen")
    public ResponseEntity<Void> reopenProject(@PathVariable UUID projectId, @RequestHeader String username) {
        projectService.changeProjectStatus(projectId, username, true);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{projectId}/{username}")
    public ResponseEntity<ProjectUser> getProjectUser(@PathVariable UUID projectId,
                                                      @PathVariable String username) {
        val response = projectService.getProjectUser(projectId, username);
        return ResponseEntity.ok(response);
    }
}
