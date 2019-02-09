package com.heavenhr.repository;

import com.heavenhr.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffersRepository extends JpaRepository<Offer, String> {
}
