package com.agylist.sprint.repository;

import com.agylist.sprint.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SprintRepository extends JpaRepository<Sprint, UUID> {

    List<Sprint> findAllByProjectId(UUID projectId);
}
