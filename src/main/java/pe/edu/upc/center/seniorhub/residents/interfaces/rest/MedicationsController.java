package pe.edu.upc.center.seniorhub.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.seniorhub.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.Medication;
import pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.MedicationResource;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE }, allowCredentials = "false")
@RestController
@RequestMapping("/api/v1/residents/{residentId}/medications")
@Tag(name = "Medications", description = "Medication Management Endpoints")
public class MedicationsController {

    private final ResidentRepository residentRepository;

    public MedicationsController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Operation(
            summary = "Add a medication for a resident",
            description = "Creates a medication entry for a specific resident",
            operationId = "addMedication",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Medication created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medication.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Medication> addMedication(@PathVariable Long residentId, @RequestBody MedicationResource resource) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with id: " + residentId));

        Medication medication = new Medication(resource.name(), resource.frequency());
        resident.addMedication(medication);
        residentRepository.save(resident);

        return new ResponseEntity<>(medication, HttpStatus.CREATED);
    }

    @Operation(
            summary = "List medications for a resident",
            description = "Returns all medications associated with a resident",
            operationId = "getMedications",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medication.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Medication>> getMedications(@PathVariable Long residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with id: " + residentId));

        return ResponseEntity.ok(resident.getMedication());
    }

    @Operation(
            summary = "Delete a medication for a resident",
            description = "Removes a medication entry by ID for a specific resident",
            operationId = "deleteMedication",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Medication deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Resident or medication not found", content = @Content)
            }
    )
    @DeleteMapping("/{medicationId}")
    public ResponseEntity<?> deleteMedication(@PathVariable Long residentId, @PathVariable Long medicationId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with id: " + residentId));

        Medication medicationToRemove = resident.getMedication().stream()
                .filter(m -> m.getId().equals(medicationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Medication not found with id: " + medicationId));

        resident.removeMedication(medicationToRemove);
        residentRepository.save(resident);

        return ResponseEntity.noContent().build();
    }
}
