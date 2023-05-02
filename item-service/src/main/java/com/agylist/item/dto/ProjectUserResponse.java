package com.agylist.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUserResponse {

    private UUID recordId;
    private UUID projectId;
    private UUID userId;
    private Role role;
}
