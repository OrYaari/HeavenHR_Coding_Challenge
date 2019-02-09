package com.heavenhr.controller;

import com.heavenhr.model.Offer;
import com.heavenhr.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/heavenhr/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @GetMapping(value = "/{jobTitle}", produces = "application/json")
    public Offer getOffer(@RequestParam String jobTitle) {
        return null;
    }

    @PostMapping
    public void createOrUpdateOffer(@RequestBody Offer offer) {

    }

    @GetMapping
    public List<Offer> getOffers() {
        return null;
    }
}
