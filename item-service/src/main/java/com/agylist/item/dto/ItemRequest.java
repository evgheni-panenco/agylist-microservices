package com.agylist.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    private UUID projectId;
    private UUID sprintId;
    private String title;
    private String description;
    private Integer storyPoints;
}
