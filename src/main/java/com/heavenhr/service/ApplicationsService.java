package com.heavenhr.service;

import com.heavenhr.model.ApplicationModel;
import com.heavenhr.repository.ApplicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    public List<ApplicationModel> getApplications() {
        return applicationsRepository.findAll().stream().map(ApplicationModel::new).collect(Collectors.toList());
    }
}
