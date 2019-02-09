package com.heavenhr.controller;

import com.heavenhr.model.Application;
import com.heavenhr.model.Offer;
import com.heavenhr.service.ApplicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/heavenhr/applications")
public class ApplicationsController {

    @Autowired
    private ApplicationsService applicationsService;

    @PostMapping
    public void createOrUpdateApplication(@RequestBody Application application) {

    }

    @GetMapping
    public List<Application> getApplications() {
        return null;
    }
}
