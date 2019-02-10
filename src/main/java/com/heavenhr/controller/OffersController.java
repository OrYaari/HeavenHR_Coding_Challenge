package com.heavenhr.controller;

import com.heavenhr.model.OfferModel;
import com.heavenhr.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/heavenhr/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @GetMapping(value = "/{jobTitle}", produces = "application/json")
    public ResponseEntity<OfferModel> getOffer(@PathVariable String jobTitle) {
        Optional<OfferModel> offer = offersService.findOffer(jobTitle);

        if (offer.isPresent()) {
            return ResponseEntity.ok(offer.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
