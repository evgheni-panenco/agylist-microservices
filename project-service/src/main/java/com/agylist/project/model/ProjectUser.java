package com.agylist.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUser {

    @Id
    private UUID recordId;
    private UUID projectId;
    private UUID userId;
    @Enumerated
    private Role role;
}
