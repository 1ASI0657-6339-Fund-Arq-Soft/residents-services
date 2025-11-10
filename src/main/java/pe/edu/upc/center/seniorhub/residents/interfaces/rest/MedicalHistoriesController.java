package pe.edu.upc.center.seniorhub.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.MedicalHistory;
import pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.MedicalHistoryResource;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE }, allowCredentials = "false")
@RestController
@RequestMapping("/api/v1/residents/{residentId}/medical-histories")
@Tag(name = "Medical Histories", description = "Resident Medical History Management Endpoints")
public class MedicalHistoriesController {

    private final ResidentRepository residentRepository;

    public MedicalHistoriesController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Operation(
            summary = "List medical histories for a resident",
            description = "Retrieves all medical histories for a resident",
            operationId = "getMedicalHistories",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicalHistory.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<MedicalHistory>> getMedicalHistories(@PathVariable Long residentId) {
        return residentRepository.findById(residentId)
                .map(resident -> ResponseEntity.ok(resident.getMedicalHistories()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add a medical history for a resident",
            description = "Creates a new medical history record for a resident",
            operationId = "addMedicalHistory",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Medical history created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicalHistory.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<List<MedicalHistory>> addMedicalHistory(@PathVariable Long residentId, @RequestBody MedicalHistoryResource resource) {
        return residentRepository.findById(residentId)
                .map(resident -> {
                    MedicalHistory medicalHistory = new MedicalHistory(new Date(), resource.diagnosis(), resource.treatment());
                    resident.addMedicalHistory(medicalHistory);
                    residentRepository.save(resident);
                    return ResponseEntity.status(HttpStatus.CREATED).body(resident.getMedicalHistories());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a medical history for a resident",
            description = "Deletes a specific medical history by ID",
            operationId = "removeMedicalHistory",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Medical history deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Resident or medical history not found", content = @Content)
            }
    )
    @DeleteMapping("/{medicalHistoryId}")
    public ResponseEntity<?> removeMedicalHistory(@PathVariable Long residentId, @PathVariable Long medicalHistoryId) {
        return residentRepository.findById(residentId)
                .map(resident -> {
                    resident.removeMedicalHistoryById(medicalHistoryId);
                    residentRepository.save(resident);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
