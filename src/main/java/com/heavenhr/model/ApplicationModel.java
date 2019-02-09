package com.heavenhr.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.heavenhr.entity.Application;

@JsonAutoDetect
public class ApplicationModel {

    private Long id;
    private OfferModel offerModel;
    private String candidateEmail;
    private String resume;
    private ApplicationStatus applicationStatus;

    protected ApplicationModel() {}

    public ApplicationModel(Long id, OfferModel offerModel, String candidateEmail, String resume, ApplicationStatus applicationStatus) {
        this.id = id;
        this.offerModel = offerModel;
        this.candidateEmail = candidateEmail;
        this.resume = resume;
        this.applicationStatus = applicationStatus;
    }

    public ApplicationModel(Application application) {
        this(application.getId(),
                new OfferModel(application.getOffer()),
                application.getCandidateEmail(),
                application.getResume(),
                application.getApplicationStatus());
    }
}
