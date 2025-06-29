package com.availit.backend.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Schema(description = "Hospital availability information including bed and oxygen availability")
public class HospitalAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the hospital", example = "1")
    private Long id;

    @Schema(description = "Name of the hospital", example = "City General Hospital", required = true)
    private String hospitalName;
    
    @Schema(description = "Total number of beds in the hospital", example = "100", minimum = "0")
    private int totalBeds;
    
    @Schema(description = "Number of available beds", example = "25", minimum = "0")
    private int availableBeds;
    
    @Schema(description = "Whether oxygen is available at the hospital", example = "true")
    private boolean oxygenAvailable;

    @Schema(description = "Address of the hospital", example = "123 Main St, Springfield", required = true)
    @NotNull
    @Size(min = 5, max = 255)
    private String address;

    @Schema(description = "Contact number of the hospital", example = "+1-555-1234", required = true)
    @NotNull
    @Size(min = 7, max = 20)
    private String contactNumber;

    @Schema(description = "Number of ICU beds", example = "10", minimum = "0")
    @Min(0)
    private int icuBeds;

    @Schema(description = "Number of ventilators", example = "5", minimum = "0")
    @Min(0)
    private int ventilators;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hospital_id")
    private List<Location> locations;

    // Constructors
    public HospitalAvailability() {}

    public HospitalAvailability(String hospitalName, int totalBeds, int availableBeds, boolean oxygenAvailable, String address, String contactNumber, int icuBeds, int ventilators) {
        this.hospitalName = hospitalName;
        this.totalBeds = totalBeds;
        this.availableBeds = availableBeds;
        this.oxygenAvailable = oxygenAvailable;
        this.address = address;
        this.contactNumber = contactNumber;
        this.icuBeds = icuBeds;
        this.ventilators = ventilators;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public boolean isOxygenAvailable() {
        return oxygenAvailable;
    }

    public void setOxygenAvailable(boolean oxygenAvailable) {
        this.oxygenAvailable = oxygenAvailable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getIcuBeds() {
        return icuBeds;
    }

    public void setIcuBeds(int icuBeds) {
        this.icuBeds = icuBeds;
    }

    public int getVentilators() {
        return ventilators;
    }

    public void setVentilators(int ventilators) {
        this.ventilators = ventilators;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
