package com.heavenhr.repository;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.ApplicationId;
import com.heavenhr.model.ApplicationStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.heavenhr.model.ApplicationStatus.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class ApplicationsRepositoryTest {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Before
    public void setUp() throws Exception {
        Application app1 = new Application(new ApplicationId("dev", "myMail@mail.com"), "Hire me", APPLIED);
        Application app2 = new Application(new ApplicationId("developer", "myMail@mail.com"), "Hire me", INVITED);
        Application app3 = new Application(new ApplicationId("dev", "notMyMail@mail.com"), "Hire me", REJECTED);
        applicationsRepository.save(app1);
        applicationsRepository.save(app2);
        applicationsRepository.save(app3);
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_allNull() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus(null, null, null);
        assertEquals(3, applications.size());
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_justOffer() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus("dev", null, null);
        assertEquals(2, applications.size());
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_justMail() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus(null, "notMyMail@mail.com", null);
        assertEquals(1, applications.size());
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_justApplicationStatus() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus( null, null, INVITED);
        assertEquals(1, applications.size());
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_offerAndMail() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus( "dev", "myMail@mail.com", null);
        assertEquals(1, applications.size());
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_mailAndApplicationStatus() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus( null, "myMail@mail.com", INVITED);
        assertEquals(1, applications.size());
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_offerAndApplicationStatus() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus( "dev", null, APPLIED);
        assertEquals(1, applications.size());
    }

    @Test
    public void findByOfferAndEmailAndApplicationStatus_all() {
        List<Application> applications = applicationsRepository.findByOfferAndEmailAndApplicationStatus( "dev", "myMail@mail.com", APPLIED);
        assertEquals(1, applications.size());
    }
}