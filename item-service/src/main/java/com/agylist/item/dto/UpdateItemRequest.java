package com.agylist.item.dto;

import com.agylist.item.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {

    private UUID sprintId;
    private UUID assignedUserId;
    private String title;
    private String description;
    private Integer storyPoints;
    private Status status;
}
