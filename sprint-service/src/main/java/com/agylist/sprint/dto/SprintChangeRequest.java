package com.agylist.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintChangeRequest {

    private String name;
    private String goals;

    private Date startDate;
    private Date endDate;
}
