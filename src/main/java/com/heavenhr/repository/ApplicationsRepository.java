package com.heavenhr.repository;

import com.heavenhr.entity.Application;
import com.heavenhr.entity.ApplicationId;
import com.heavenhr.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationsRepository extends JpaRepository<Application, ApplicationId> {

    @Query("SELECT app " +
            "FROM Application app " +
            "WHERE (:offer is null or app.applicationId.offer = :offer) " +
            "AND (:email is null or app.applicationId.candidateEmail = :email) " +
            "AND (:applicationStatus is null or app.applicationStatus = :applicationStatus)")
    List<Application> findByOfferAndEmailAndApplicationStatus(String offer, String email, ApplicationStatus applicationStatus);
}
