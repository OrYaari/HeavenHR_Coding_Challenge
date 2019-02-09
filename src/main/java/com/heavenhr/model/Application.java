package com.heavenhr.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Application {
    private Offer offer;
    private String candidateEmail;
    private String resume;
    private ApplicationStatus applicationStatus;
}
