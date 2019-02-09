package com.heavenhr.repository;

import com.heavenhr.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationsRepository extends JpaRepository<Application, Long> {
}
