package com.heavenhr.entity;

import com.heavenhr.model.OfferModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Offer {

    @Id
    private String jobTitle;
    private LocalDate startDate;
    private int numberOfApplications;

    protected Offer() {}

    public Offer(String jobTitle, LocalDate startDate, int numberOfApplications) {
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.numberOfApplications = numberOfApplications;
    }

    public Offer(OfferModel offerModel) {
        this(offerModel.getJobTitle(), offerModel.getStartDate(), offerModel.getNumberOfApplications());
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getNumberOfApplications() {
        return numberOfApplications;
    }
}
