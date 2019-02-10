package com.heavenhr.controller;

import com.heavenhr.model.ApplicationModel;
import com.heavenhr.model.ApplicationStatus;
import com.heavenhr.service.ApplicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/heavenhr/applications")
public class ApplicationsController {

    @Autowired
    private ApplicationsService applicationsService;

    @PostMapping
    @ResponseBody
    public String createOrUpdateApplication(@RequestBody ApplicationModel applicationModel) {
        return applicationsService.applyForOffer(applicationModel);
    }

    @GetMapping
    @ResponseBody
    public List<ApplicationModel> getApplications(@RequestParam(value = "offer", required = false) String offer,
                                                  @RequestParam(value = "email", required = false) String email,
                                                  @RequestParam(value = "applicationStatus", required = false) ApplicationStatus applicationStatus) {
        return applicationsService.getApplications(offer, email, applicationStatus);
    }
}
