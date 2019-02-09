package com.heavenhr.service;

import com.heavenhr.entity.Offer;
import com.heavenhr.model.OfferModel;
import com.heavenhr.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OffersService {

    @Autowired
    private OffersRepository offersRepository;

    public List<OfferModel> getOffers() {
        return offersRepository.findAll().stream().map(OfferModel::new).collect(Collectors.toList());
    }

    public boolean addOffer(OfferModel offerModel) {
        offersRepository.save(new Offer(offerModel));

        return true;
    }

    public Optional<OfferModel> findOffer(String jobTitle) {
        Optional<OfferModel> response = Optional.empty();
        Optional<Offer> offer = offersRepository.findById(jobTitle);
        if (offer.isPresent()) {
            response = Optional.of(new OfferModel(offer.get()));
        }

        return response;
    }
}
