package com.heavenhr.entity;

import com.heavenhr.model.ApplicationModel;
import com.heavenhr.model.ApplicationStatus;

import javax.persistence.*;

@Entity
public class Application {

    @EmbeddedId
    private ApplicationId applicationId;
    private String resume;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    protected Application() {}

    public Application(ApplicationId applicationId, String resume, ApplicationStatus applicationStatus) {
        this.applicationId = applicationId;
        this.resume = resume;
        this.applicationStatus = applicationStatus;
    }

    public Application(ApplicationModel applicationModel) {
        this.applicationId = new ApplicationId(applicationModel.getOffer(), applicationModel.getCandidateEmail());
        this.resume = applicationModel.getResume();
        this.applicationStatus = applicationModel.getApplicationStatus();
    }

    public String getOffer() {
        return applicationId.getOffer();
    }

    public String getCandidateEmail() {
        return applicationId.getCandidateEmail();
    }

    public String getResume() {
        return resume;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
