package com.heavenhr.service;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.Offer;
import com.heavenhr.model.ApplicationModel;
import com.heavenhr.model.ApplicationStatus;
import com.heavenhr.repository.ApplicationsRepository;
import com.heavenhr.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private OffersRepository offersRepository;

    public void applyForOffer(ApplicationModel applicationModel) {
        Optional<Offer> offer = offersRepository.findById(applicationModel.getOffer());

        if (offer.isPresent()) {
            applicationsRepository.save(new Application(applicationModel));
        }
    }

    public List<ApplicationModel> getApplications(String offer, String email, ApplicationStatus applicationStatus) {
        return applicationsRepository.findByOfferAndEmailAndApplicationStatus(offer, email, applicationStatus).stream()
                .map(ApplicationModel::new)
                .collect(Collectors.toList());
    }
}
