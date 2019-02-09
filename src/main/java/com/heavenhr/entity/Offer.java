package com.heavenhr.entity;

import com.heavenhr.model.OfferModel;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Offer {

    @Id
    private String jobTitle;
    private LocalDate startDate;

    @Formula("(select count(*) from application a where a.offer = job_title)")
    private int numberOfApplications;

    protected Offer() {}

    public Offer(String jobTitle, LocalDate startDate) {
        this.jobTitle = jobTitle;
        this.startDate = startDate;
    }

    public Offer(OfferModel offerModel) {
        this(offerModel.getJobTitle(), offerModel.getStartDate());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return  jobTitle.equals(offer.jobTitle) &&
                startDate.equals(offer.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobTitle, startDate);
    }
}
