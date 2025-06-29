package com.availit.backend.service;

import com.availit.backend.model.HospitalAvailability;
import com.availit.backend.repository.HospitalAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalAvailabilityService {

    @Autowired
    private HospitalAvailabilityRepository repository;

    public List<HospitalAvailability> getAllHospitals() {
        return repository.findAll();
    }

    public Page<HospitalAvailability> getAllHospitals(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public HospitalAvailability createHospital(HospitalAvailability hospital) {
        return repository.save(hospital);
    }

    public HospitalAvailability getHospitalById(Long id) {
        Optional<HospitalAvailability> hospital = repository.findById(id);
        return hospital.orElse(null);
    }

    public HospitalAvailability updateHospital(Long id, HospitalAvailability hospitalDetails) {
        Optional<HospitalAvailability> hospital = repository.findById(id);
        if (hospital.isPresent()) {
            HospitalAvailability existingHospital = hospital.get();
            existingHospital.setHospitalName(hospitalDetails.getHospitalName());
            existingHospital.setTotalBeds(hospitalDetails.getTotalBeds());
            existingHospital.setAvailableBeds(hospitalDetails.getAvailableBeds());
            existingHospital.setOxygenAvailable(hospitalDetails.isOxygenAvailable());
            return repository.save(existingHospital);
        }
        return null;
    }

    public boolean deleteHospital(Long id) {
        Optional<HospitalAvailability> hospital = repository.findById(id);
        if (hospital.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
