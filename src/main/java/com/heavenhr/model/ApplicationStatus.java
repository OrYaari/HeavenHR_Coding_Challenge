package com.heavenhr.model;

public enum ApplicationStatus {
    APPLIED("Candidate applied for the offer"),
    INVITED("Candidate was invited"),
    REJECTED("Candidate was rejected for this job offer"),
    HIRED("Congratulations candidate was hired");

    private String applicationProgressionMessage;

    private ApplicationStatus(String applicationProgressionMessage) {
        this.applicationProgressionMessage = applicationProgressionMessage;
    }

    public String getApplicationProgressionMessage() {
        return applicationProgressionMessage;
    }
}
