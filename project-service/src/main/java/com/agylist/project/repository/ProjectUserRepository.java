package com.agylist.project.repository;

import com.agylist.project.model.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    Optional<ProjectUser> findByProjectIdAndUserId(UUID projectId, UUID userId);
    List<ProjectUser> findAllByUserId(UUID userId);
}
