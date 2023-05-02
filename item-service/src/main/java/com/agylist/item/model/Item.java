package com.agylist.item.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    private UUID id;
    private UUID projectId;
    private UUID sprintId;
    private UUID assignedUserId;
    private UUID reporterUserId;
    private String title;
    private String description;

    private Integer storyPoints;
    @Enumerated
    private Status status;

    private OffsetDateTime createdOn;
}
