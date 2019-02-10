package com.heavenhr.service;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.ApplicationId;
import com.heavenhr.entity.Offer;
import com.heavenhr.model.ApplicationModel;
import com.heavenhr.repository.ApplicationsRepository;
import com.heavenhr.repository.OffersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.heavenhr.model.ApplicationStatus.APPLIED;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationsServiceTest {

    @InjectMocks
    private ApplicationsService applicationsService;

    @Mock
    private ApplicationsRepository applicationsRepository;

    @Mock
    private OffersRepository offersRepository;

    private ApplicationModel applicationModel;

    private Offer offer;

    private Application application;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        applicationModel = new ApplicationModel("dev", "myMail@mail.com", "resume", APPLIED);
        offer = new Offer("dev", LocalDate.now());
        application = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", APPLIED);
    }

    @Test
    public void applyForOffer_withNoOffer() {
        when(offersRepository.findById(any())).thenReturn(Optional.empty());

        assertEquals("There is no such offer", applicationsService.applyForOffer(applicationModel));
    }

    @Test
    public void applyForOffer_success() {
        when(offersRepository.findById(any())).thenReturn(Optional.of(offer));
        when(applicationsRepository.findById(any())).thenReturn(Optional.empty());

        assertEquals("Candidate applied successfully", applicationsService.applyForOffer(applicationModel));
    }

    @Test
    public void applyForOffer_alreadyExists() {
        when(offersRepository.findById(any())).thenReturn(Optional.of(offer));
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(application));

        assertEquals("Application already applied", applicationsService.applyForOffer(applicationModel));
    }

    @Test
    public void getApplications() {
        when(applicationsRepository.findByOfferAndEmailAndApplicationStatus(any(), any(), any()))
                .thenReturn(Collections.singletonList(application));
        List<ApplicationModel> applications = applicationsService.getApplications(null, null, null);

        assertEquals(1, applications.size());
        assertEquals("dev", applications.get(0).getOffer());
    }
}