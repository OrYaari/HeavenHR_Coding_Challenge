package com.heavenhr.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ProgressApplication {
    private String offer;
    private String candidateEmail;
    private ApplicationStatus applicationStatus;

    protected ProgressApplication() {
    }

    public ProgressApplication(String offer, String candidateEmail, ApplicationStatus applicationStatus) {
        this.offer = offer;
        this.candidateEmail = candidateEmail;
        this.applicationStatus = applicationStatus;
    }

    public String getOffer() {
        return offer;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }
}
