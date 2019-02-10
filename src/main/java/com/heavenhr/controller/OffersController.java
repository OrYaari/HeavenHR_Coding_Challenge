package com.heavenhr.controller;

import com.heavenhr.model.OfferModel;
import com.heavenhr.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/heavenhr/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @GetMapping(value = "/{jobTitle}", produces = "application/json")
    @ResponseBody
    public OfferModel getOffer(@PathVariable String jobTitle) {
        Optional<OfferModel> offer = offersService.findOffer(jobTitle);
        return offer.orElse(null);
    }

    @PostMapping
    public void createOffer(@RequestBody OfferModel offerModel) {
        offersService.addOffer(offerModel);
    }

    @GetMapping
    @ResponseBody
    public List<OfferModel> getOffers() {
        return offersService.getOffers();
    }
}
