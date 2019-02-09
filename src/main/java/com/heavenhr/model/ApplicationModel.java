package com.heavenhr.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.heavenhr.entity.Application;

@JsonAutoDetect
public class ApplicationModel {

    private String offer;
    private String candidateEmail;
    private String resume;
    private ApplicationStatus applicationStatus = ApplicationStatus.APPLIED;

    protected ApplicationModel() {}

    public ApplicationModel(String offer, String candidateEmail, String resume, ApplicationStatus applicationStatus) {
        this.offer = offer;
        this.candidateEmail = candidateEmail;
        this.resume = resume;
        this.applicationStatus = applicationStatus;
    }

    public ApplicationModel(Application application) {
        this(application.getOffer(),
             application.getCandidateEmail(),
             application.getResume(),
             application.getApplicationStatus());
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
