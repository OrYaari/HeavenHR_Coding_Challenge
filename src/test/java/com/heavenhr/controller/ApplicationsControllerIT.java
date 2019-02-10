package com.heavenhr.controller;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.ApplicationId;
import com.heavenhr.entity.Offer;
import com.heavenhr.model.ApplicationStatus;
import com.heavenhr.repository.ApplicationsRepository;
import com.heavenhr.repository.OffersRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.heavenhr.model.ApplicationStatus.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private ApplicationsRepository applicationsRepository;

    private static final String APPLICATION_JSON = "application/json";

    @Before
    public void setUp() throws Exception {
        Offer offer = new Offer("dev", LocalDate.now());
        Offer offer2 = new Offer("dev2", LocalDate.now());
        offersRepository.save(offer);
        offersRepository.save(offer2);
    }

    @Test
    public void apply_success() throws Exception {
        mockMvc.perform(post("/api/v1/heavenhr/applications").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"resume\":\"resume\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string("Candidate applied successfully"));
    }

    @Test
    public void apply_noOffer() throws Exception {
        mockMvc.perform(post("/api/v1/heavenhr/applications").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev5\", \"candidateEmail\": \"myMail@gmail.com\", \"resume\":\"resume\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string("There is no such offer"));
    }

    @Test
    public void apply_alreadyApplied() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        mockMvc.perform(post("/api/v1/heavenhr/applications").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"resume\":\"resume\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string("Application already applied"));
    }

    @Test
    public void getApplications_noFilters() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        mockMvc.perform(get("/api/v1/heavenhr/applications")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().json("[{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"resume\":\"resume\", \"applicationStatus\": \"APPLIED\"}]"));
    }

    @Test
    public void getApplications_jobFilter() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        Application application2 = new Application(new ApplicationId("dev2", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        applicationsRepository.save(application2);
        mockMvc.perform(get("/api/v1/heavenhr/applications?offer=dev")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().json("[{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"resume\":\"resume\", \"applicationStatus\": \"APPLIED\"}]"));
    }

    @Test
    public void getApplications_mailFilter() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        Application application2 = new Application(new ApplicationId("dev", "myMail2@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        applicationsRepository.save(application2);
        mockMvc.perform(get("/api/v1/heavenhr/applications?email=myMail2@gmail.com")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().json("[{\"offer\": \"dev\", \"candidateEmail\": \"myMail2@gmail.com\", \"resume\":\"resume\", \"applicationStatus\": \"APPLIED\"}]"));
    }

    @Test
    public void getApplications_applicationFilter() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        Application application2 = new Application(new ApplicationId("dev", "myMail2@gmail.com"), "resume", INVITED);
        applicationsRepository.save(application);
        applicationsRepository.save(application2);
        mockMvc.perform(get("/api/v1/heavenhr/applications?applicationStatus=INVITED")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().json("[{\"offer\": \"dev\", \"candidateEmail\": \"myMail2@gmail.com\", \"resume\":\"resume\", \"applicationStatus\": \"INVITED\"}]"));
    }

    @Test
    public void progressApplication_appliedToInvited() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        mockMvc.perform(post("/api/v1/heavenhr/applications/progress").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"applicationStatus\":\"INVITED\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string(INVITED.getApplicationProgressionMessage()));
    }

    @Test
    public void progressApplication_appliedToRejected() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        mockMvc.perform(post("/api/v1/heavenhr/applications/progress").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"applicationStatus\":\"REJECTED\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string(REJECTED.getApplicationProgressionMessage()));
    }

    @Test
    public void progressApplication_appliedToHired() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        mockMvc.perform(post("/api/v1/heavenhr/applications/progress").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"applicationStatus\":\"HIRED\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string(HIRED.getApplicationProgressionMessage()));
    }

    @Test
    public void progressApplication_noProgression() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        mockMvc.perform(post("/api/v1/heavenhr/applications/progress").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev\", \"candidateEmail\": \"myMail@gmail.com\", \"applicationStatus\":\"APPLIED\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string("Application couldn't progress to status APPLIED"));
    }

    @Test
    public void progressApplication_noSuchCandidate() throws Exception {
        Application application = new Application(new ApplicationId("dev", "myMail@gmail.com"), "resume", APPLIED);
        applicationsRepository.save(application);
        mockMvc.perform(post("/api/v1/heavenhr/applications/progress").
                contentType(APPLICATION_JSON).
                content("{\"offer\": \"dev\", \"candidateEmail\": \"myMail555@gmail.com\", \"applicationStatus\":\"INVITED\"}")).
                andExpect(status().is2xxSuccessful()).
                andExpect(content().string("Application candidate does not exists"));
    }

    @After
    public void tearDown() throws Exception {
        applicationsRepository.deleteAll();
    }
}