package com.heavenhr.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApplicationId implements Serializable {

    @NotNull
    private String offer;

    @NotNull
    private String candidateEmail;

    public ApplicationId() {
    }

    public ApplicationId(@NotNull String offer, @NotNull String candidateEmail) {
        this.offer = offer;
        this.candidateEmail = candidateEmail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationId that = (ApplicationId) o;
        return offer.equals(that.offer) &&
                candidateEmail.equals(that.candidateEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offer, candidateEmail);
    }
}
