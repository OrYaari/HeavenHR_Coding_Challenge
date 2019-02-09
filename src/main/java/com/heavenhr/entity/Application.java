package com.heavenhr.entity;

import com.heavenhr.model.ApplicationStatus;

import javax.persistence.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Offer offer;
    private String candidateEmail;
    private String resume;
    private ApplicationStatus applicationStatus;

    protected Application() {}

    public Long getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public String getResume() {
        return resume;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }
}
