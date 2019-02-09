package com.heavenhr.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.heavenhr.entity.Offer;

import java.time.LocalDate;

@JsonAutoDetect
public class OfferModel {

    private String jobTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    private int numberOfApplications;

    protected OfferModel() {}

    public OfferModel(String jobTitle, LocalDate startDate, int numberOfApplications) {
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.numberOfApplications = numberOfApplications;
    }

    public OfferModel(Offer offer) {
        this(offer.getJobTitle(), offer.getStartDate(), offer.getNumberOfApplications());
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
