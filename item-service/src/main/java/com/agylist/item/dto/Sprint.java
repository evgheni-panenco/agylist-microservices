package com.agylist.item.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sprint {

    private UUID id;
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
