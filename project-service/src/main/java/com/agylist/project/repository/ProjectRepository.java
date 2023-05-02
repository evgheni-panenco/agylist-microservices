package com.agylist.project.repository;

import com.agylist.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findAllByIdIsIn(List<UUID> ids);

}
