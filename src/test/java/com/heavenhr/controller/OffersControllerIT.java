package com.heavenhr.controller;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.ApplicationId;
import com.heavenhr.entity.Offer;
import com.heavenhr.model.ApplicationStatus;
import com.heavenhr.repository.ApplicationsRepository;
import com.heavenhr.repository.OffersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.heavenhr.model.ApplicationStatus.APPLIED;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OffersControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private ApplicationsRepository applicationsRepository;

    private static final String APPLICATION_JSON = "application/json";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getOffer_found_noApplications() throws Exception {
        Offer offer = new Offer("dev", LocalDate.of(2019, 2, 10));
        offersRepository.save(offer);

        mockMvc.perform(get("/api/v1/heavenhr/offers/dev")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().json("{\"jobTitle\": \"dev\", \"startDate\": \"10/02/2019\", \"numberOfApplications\" = 0}"));
    }

    @Test
    public void getOffer_found_withApplications() throws Exception {
        Offer offer = new Offer("dev", LocalDate.of(2019, 2, 10));
        offersRepository.save(offer);
        Application application = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", APPLIED);
        applicationsRepository.save(application);

        mockMvc.perform(get("/api/v1/heavenhr/offers/dev")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().json("{\"jobTitle\": \"dev\", \"startDate\": \"10/02/2019\", \"numberOfApplications\" = 1}"));
    }

    @Test
    public void getOffer_notFound() throws Exception {
        mockMvc.perform(get("/api/v1/heavenhr/offers/dev")).
                andExpect(status().isNotFound());
    }

    @Test
    public void createOffer() throws Exception {
        mockMvc.perform(post("/api/v1/heavenhr/offers").
                contentType(APPLICATION_JSON).
                content("{\"jobTitle\": \"dev\", \"startDate\": \"10/02/2019\"}")).
                andExpect(status().is2xxSuccessful());

        assertEquals(1, offersRepository.findAll().size());
    }

    @Test
    public void getOffers() throws Exception {
        Offer offer = new Offer("dev", LocalDate.of(2019, 2, 10));
        offersRepository.save(offer);

        mockMvc.perform(get("/api/v1/heavenhr/offers")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().json("[{\"jobTitle\": \"dev\", \"startDate\": \"10/02/2019\", \"numberOfApplications\" = 0}]"));
    }
}