package com.agylist.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintRequest {

    private UUID projectId;
    private String name;
    private String goals;

    private Date startDate;
    private Date endDate;

    private boolean isStarted;
    private boolean isClosed;

    private OffsetDateTime createdOn;
    private OffsetDateTime closedOn;
}
