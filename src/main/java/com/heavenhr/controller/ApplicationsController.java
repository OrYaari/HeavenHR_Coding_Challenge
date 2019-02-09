package com.heavenhr.controller;

import com.heavenhr.model.ApplicationModel;
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
    public void createOrUpdateApplication(@RequestBody ApplicationModel applicationModel) {
        applicationsService.applyForOffer(applicationModel);
    }

    @GetMapping
    public List<ApplicationModel> getApplications() {
        return applicationsService.getApplications();
    }
}
