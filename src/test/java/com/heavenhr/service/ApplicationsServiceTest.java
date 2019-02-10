package com.heavenhr.service;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.ApplicationId;
import com.heavenhr.entity.Offer;
import com.heavenhr.model.ApplicationModel;
import com.heavenhr.model.ProgressApplication;
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

import static com.heavenhr.model.ApplicationStatus.*;
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

    @Test
    public void progressApplication_applicationCandidateNotExists() {
        when(applicationsRepository.findById(any())).thenReturn(Optional.empty());

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", INVITED);

        assertEquals("Application candidate does not exists", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_appliedToApplied() {
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(application));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", APPLIED);

        assertEquals("Application couldn't progress to status APPLIED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_appliedToInvited() {
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(application));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", INVITED);

        assertEquals(INVITED.getApplicationProgressionMessage(), applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_appliedToRejected() {
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(application));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", REJECTED);

        assertEquals(REJECTED.getApplicationProgressionMessage(), applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_appliedToHired() {
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(application));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", HIRED);

        assertEquals(HIRED.getApplicationProgressionMessage(), applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_invitedToApplied() {
        Application invitedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", INVITED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(invitedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", APPLIED);

        assertEquals("Application couldn't progress to status APPLIED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_invitedToInvited() {
        Application invitedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", INVITED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(invitedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", INVITED);

        assertEquals("Application couldn't progress to status INVITED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_invitedToRejected() {
        Application invitedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", INVITED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(invitedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", REJECTED);

        assertEquals(REJECTED.getApplicationProgressionMessage(), applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_invitedToHired() {
        Application invitedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", INVITED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(invitedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", HIRED);

        assertEquals(HIRED.getApplicationProgressionMessage(), applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_rejectedToApplied() {
        Application rejectedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", REJECTED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(rejectedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", APPLIED);

        assertEquals("Application couldn't progress to status APPLIED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_rejectedToInvited() {
        Application rejectedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", REJECTED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(rejectedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", INVITED);

        assertEquals("Application couldn't progress to status INVITED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_rejectedToRejected() {
        Application rejectedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", REJECTED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(rejectedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", REJECTED);

        assertEquals("Application couldn't progress to status REJECTED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_rejectedToHired() {
        Application rejectedApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", REJECTED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(rejectedApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", HIRED);

        assertEquals("Application couldn't progress to status HIRED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_hiredToApplied() {
        Application hiredApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", HIRED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(hiredApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", APPLIED);

        assertEquals("Application couldn't progress to status APPLIED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_hiredToInvited() {
        Application hiredApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", HIRED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(hiredApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", INVITED);

        assertEquals("Application couldn't progress to status INVITED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_hiredToRejected() {
        Application hiredApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", HIRED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(hiredApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", REJECTED);

        assertEquals("Application couldn't progress to status REJECTED", applicationsService.progressApplication(progressApplication));
    }

    @Test
    public void progressApplication_hiredToHired() {
        Application hiredApp = new Application(new ApplicationId("dev", "myMail@mail.com"), "resume", HIRED);
        when(applicationsRepository.findById(any())).thenReturn(Optional.of(hiredApp));

        ProgressApplication progressApplication = new ProgressApplication("dev", "myMail@mail.com", HIRED);

        assertEquals("Application couldn't progress to status HIRED", applicationsService.progressApplication(progressApplication));
    }
}