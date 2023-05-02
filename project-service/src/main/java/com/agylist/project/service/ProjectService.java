package com.agylist.project.service;

import com.agylist.project.dto.ProjectRequest;
import com.agylist.project.exception.NotEligibleException;
import com.agylist.project.exception.ResourceNotFoundException;
import com.agylist.project.integration.UserServiceClient;
import com.agylist.project.model.Project;
import com.agylist.project.model.ProjectUser;
import com.agylist.project.model.Role;
import com.agylist.project.repository.ProjectRepository;
import com.agylist.project.repository.ProjectUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;

    private final UserServiceClient userServiceClient;

    public List<Project> getAllProjects(String username) {
        val userId = userServiceClient.getUserByUsername(username).getId();
        val projects = projectUserRepository.findAllByUserId(userId);
        val projectsIds = projects.stream().map(ProjectUser::getProjectId).toList();
        return projectRepository.findAllByIdIsIn(projectsIds);
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project doesn't exist"));
    }

    public ProjectUser getProjectUser(UUID projectId, String username) {
        val user = userServiceClient.getUserByUsername(username);
        val userProject = projectUserRepository.findByProjectIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserProject not found"));
        return userProject;
    }

    public Project updateProject(UUID projectId, ProjectRequest request, String username) {
        val user = userServiceClient.getUserByUsername(username);
        val projectUserRole = projectUserRepository.findByProjectIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User assigned to project " +
                        "doesn't exist")).getRole();

        if (projectUserRole == Role.MANAGER || projectUserRole == Role.OWNER) {
            val project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project doesn't exist"));

            project.setName(request.getName());
            project.setDescription(request.getDescription());

            projectRepository.save(project);

            return project;
        } else {
            throw new NotEligibleException("User not eligible to perform such action");
        }
    }

    public void changeProjectStatus(UUID projectId, String username, boolean isActive) {
        val user = userServiceClient.getUserByUsername(username);
        val userProject = projectUserRepository.findByProjectIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User assigned to project " +
                        "doesn't exist"));

        if (userProject.getRole() == Role.OWNER) {
            val project =
                    projectRepository.findById(projectId)
                            .orElseThrow(() -> new ResourceNotFoundException("Project doesn't exist"));
            project.setActive(isActive);
            projectRepository.save(project);
        } else {
            throw new NotEligibleException("User not eligible to perform such action");
        }
    }

    @Transactional
    public void createNewProject(ProjectRequest request, String username) {
        val project = Project.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .isActive(true)
                .createdOn(OffsetDateTime.now())
                .build();
        projectRepository.save(project);

        val user = userServiceClient.getUserByUsername(username);
        val projectUser = ProjectUser.builder()
                .recordId(UUID.randomUUID())
                .projectId(project.getId())
                .userId(user.getId())
                .role(Role.OWNER)
                .build();
        projectUserRepository.save(projectUser);
    }

    public void addNewUserToProject(UUID projectId, String username, String actionUsername) {
        val project = projectRepository.findById(projectId);
        project.ifPresent(p -> {
            val isEligible = checkIfUserIsEligibleToPerformAction(actionUsername, projectId,
                    Role.MANAGER, Role.OWNER);

            if (isEligible) {
                val user = userServiceClient.getUserByUsername(username);
                val entity = projectUserRepository.findByProjectIdAndUserId(projectId,
                        user.getId());

                if (entity.isPresent()) {
                    throw new RuntimeException("User is already part of the project");
                }

                val projectUser = ProjectUser.builder()
                        .recordId(UUID.randomUUID())
                        .projectId(projectId)
                        .userId(user.getId())
                        .role(Role.USER)
                        .build();
                projectUserRepository.save(projectUser);
            } else {
                throw new NotEligibleException("User not eligible to perform such action");
            }
        });
    }

    public void changeUserRole(UUID projectId, String username, Role newRole,
                                      String actionUsername) {
        val user = userServiceClient.getUserByUsername(username);
        val entity = projectUserRepository.findByProjectIdAndUserId(projectId,
                user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User assigned to project " +
                "doesn't exist"));

        val roleRestriction = entity.getRole() == Role.USER
                ? new Role[]{Role.MANAGER, Role.OWNER}
                : new Role[]{Role.OWNER};

        val isEligible = checkIfUserIsEligibleToPerformAction(actionUsername, projectId, roleRestriction);
        if (isEligible) {
            entity.setRole(newRole);
            projectUserRepository.save(entity);
        } else {
            throw new NotEligibleException("User not eligible to perform such action");
        }
    }

    public void removeUserFromProject(UUID projectId, String username, String actionUsername) {
        val user = userServiceClient.getUserByUsername(username);
        val entity = projectUserRepository.findByProjectIdAndUserId(projectId,
                user.getId()).orElseThrow(() -> new ResourceNotFoundException("User assigned to project " +
                "doesn't exist"));

        val roleRestriction = entity.getRole() == Role.USER
                ? new Role[]{Role.MANAGER, Role.OWNER}
                : new Role[]{Role.OWNER};

        val isEligible = checkIfUserIsEligibleToPerformAction(actionUsername, projectId, roleRestriction);
        if (isEligible) {
            projectUserRepository.delete(entity);
        } else {
            throw new NotEligibleException("User not eligible to perform such action");
        }
    }

    private boolean checkIfUserIsEligibleToPerformAction(String actionUsername, UUID projectId,
                                                         Role... requiredRoles) {
        val manager = userServiceClient.getUserByUsername(actionUsername);
        val managerRole = projectUserRepository.findByProjectIdAndUserId(
                projectId, manager.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User assigned to project doesn't exist"))
                .getRole();

        return Arrays.stream(requiredRoles).anyMatch(role -> managerRole == role);
    }
}
