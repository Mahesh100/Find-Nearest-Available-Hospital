package com.availit.backend.repository;

import com.availit.backend.model.HospitalAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalAvailabilityRepository extends JpaRepository<HospitalAvailability, Long> {
}
