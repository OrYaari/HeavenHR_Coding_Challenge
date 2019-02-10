package com.heavenhr.service;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.ApplicationId;
import com.heavenhr.entity.Offer;
import com.heavenhr.model.ApplicationModel;
import com.heavenhr.model.ApplicationStatus;
import com.heavenhr.model.ProgressApplication;
import com.heavenhr.repository.ApplicationsRepository;
import com.heavenhr.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.heavenhr.model.ApplicationStatus.APPLIED;

@Service
public class ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private OffersRepository offersRepository;

    public String applyForOffer(ApplicationModel applicationModel) {
        Optional<Offer> offer = offersRepository.findById(applicationModel.getOffer());

        if (offer.isPresent()) {
            if (applicationsRepository.findById(new ApplicationId(applicationModel.getOffer(), applicationModel.getCandidateEmail())).isPresent()) {
                return "Application already applied";
            }
            applicationsRepository.save(new Application(applicationModel));

            return "Candidate applied successfully";
        } else {
            return "There is no such offer";
        }
    }

    public List<ApplicationModel> getApplications(String offer, String email, ApplicationStatus applicationStatus) {
        return applicationsRepository.findByOfferAndEmailAndApplicationStatus(offer, email, applicationStatus).stream()
                .map(ApplicationModel::new)
                .collect(Collectors.toList());
    }

    public String progressApplication(ProgressApplication progressApplication) {
        Optional<Application> applicationOptional =
                applicationsRepository.findById(new ApplicationId(progressApplication.getOffer(), progressApplication.getCandidateEmail()));

        if (!applicationOptional.isPresent()) {
            return "Application candidate does not exists";
        }

        Application application = applicationOptional.get();

        if (canProgressApplication(application.getApplicationStatus(), progressApplication.getApplicationStatus())) {
            application.setApplicationStatus(progressApplication.getApplicationStatus());
            applicationsRepository.save(application);
            return progressApplication.getApplicationStatus().getApplicationProgressionMessage();
        }

        return "Application couldn't progress to status " + progressApplication.getApplicationStatus().name();
    }

    private boolean canProgressApplication(ApplicationStatus oldApplicationStatus, ApplicationStatus newApplicationStatus) {

        if (oldApplicationStatus == newApplicationStatus) {
            return false;
        }

        switch (oldApplicationStatus) {
            case APPLIED:
                return true;
            case INVITED:
                if (newApplicationStatus != APPLIED) {
                    return true;
                }
                break;
            case REJECTED:
            case HIRED:
                return false;
        }
        return false;
    }
}
