package com.heavenhr.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;

@JsonAutoDetect
public class Offer {
    private String jobTitle;
    private LocalDateTime startDate;
    private int numberOfApplications;
}
