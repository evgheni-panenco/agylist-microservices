package com.agylist.identity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String type;
    private String message;
    private OffsetDateTime offsetDateTime;
}
