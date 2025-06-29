package com.availit.backend.controller;

import com.availit.backend.model.HospitalAvailability;
import com.availit.backend.service.HospitalAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin(origins = "*")
@Tag(name = "Hospital Availability", description = "APIs for managing hospital bed and oxygen availability")
public class HospitalAvailabilityController {

    @Autowired
    private HospitalAvailabilityService hospitalAvailabilityService;

    @GetMapping("/getAllHospitals")
    @Operation(
        summary = "Get all hospitals",
        description = "Retrieves a list of all hospitals with their bed and oxygen availability information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all hospitals",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HospitalAvailability.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<Page<HospitalAvailability>> getAllHospitals(Pageable pageable) {
        Page<HospitalAvailability> hospitals = hospitalAvailabilityService.getAllHospitals(pageable);
        return ResponseEntity.ok(hospitals);
    }

    @PostMapping
    @Operation(
        summary = "Create a new hospital",
        description = "Creates a new hospital entry with bed and oxygen availability information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Hospital created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HospitalAvailability.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<HospitalAvailability> createHospital(
            @Parameter(
                description = "Hospital availability information to create",
                required = true,
                content = @Content(schema = @Schema(implementation = HospitalAvailability.class))
            )
            @RequestBody HospitalAvailability hospital) {
        HospitalAvailability createdHospital = hospitalAvailabilityService.createHospital(hospital);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHospital);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get hospital by ID",
        description = "Retrieves a specific hospital by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Hospital found successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HospitalAvailability.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Hospital not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<HospitalAvailability> getHospitalById(
            @Parameter(description = "ID of the hospital to retrieve", example = "1")
            @PathVariable Long id) {
        HospitalAvailability hospital = hospitalAvailabilityService.getHospitalById(id);
        if (hospital != null) {
            return ResponseEntity.ok(hospital);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update hospital information",
        description = "Updates an existing hospital's bed and oxygen availability information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Hospital updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HospitalAvailability.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Hospital not found"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<HospitalAvailability> updateHospital(
            @Parameter(description = "ID of the hospital to update", example = "1")
            @PathVariable Long id,
            @Parameter(
                description = "Updated hospital information",
                required = true,
                content = @Content(schema = @Schema(implementation = HospitalAvailability.class))
            )
            @RequestBody HospitalAvailability hospital) {
        HospitalAvailability updatedHospital = hospitalAvailabilityService.updateHospital(id, hospital);
        if (updatedHospital != null) {
            return ResponseEntity.ok(updatedHospital);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete hospital",
        description = "Deletes a hospital from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Hospital deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Hospital not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<Void> deleteHospital(
            @Parameter(description = "ID of the hospital to delete", example = "1")
            @PathVariable Long id) {
        boolean deleted = hospitalAvailabilityService.deleteHospital(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
