package pe.edu.upc.center.seniorhub.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.MentalHealthRecord;
import pe.edu.upc.center.seniorhub.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.MentalHealthRecordResource;

import java.util.Date;
import java.util.List;


@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE }, allowCredentials = "false")
@RestController
@RequestMapping("/api/v1/residents/{residentId}/mental-health-records")
@Tag(name = "Mental Health Records", description = "Resident Mental Health Record Management Endpoints")
public class MentalHealthRecordsController {

    private final ResidentRepository residentRepository;

    public MentalHealthRecordsController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Operation(
            summary = "List mental health records for a resident",
            description = "Retrieves all mental health records for a resident",
            operationId = "getMentalHealthRecords",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MentalHealthRecord.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<MentalHealthRecord>> getMentalHealthRecords(@PathVariable Long residentId) {
        return residentRepository.findById(residentId)
                .map(resident -> ResponseEntity.ok(resident.getMentalHealthRecords()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add a mental health record for a resident",
            description = "Creates a new mental health record for a resident",
            operationId = "addMentalHealthRecord",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Mental health record created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MentalHealthRecord.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<List<MentalHealthRecord>> addMentalHealthRecord(@PathVariable Long residentId, @RequestBody MentalHealthRecordResource resource) {
        return residentRepository.findById(residentId)
                .map(resident -> {
                    MentalHealthRecord record = new MentalHealthRecord(new Date(), resource.diagnosis(), resource.treatment());
                    resident.addMentalHealthRecord(record);
                    residentRepository.save(resident);
                    return ResponseEntity.status(HttpStatus.CREATED).body(resident.getMentalHealthRecords());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a mental health record for a resident",
            description = "Deletes a specific mental health record by ID",
            operationId = "removeMentalHealthRecord",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Mental health record deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Resident or mental health record not found", content = @Content)
            }
    )
    @DeleteMapping("/{mentalHealthRecordId}")
    public ResponseEntity<?> removeMentalHealthRecord(@PathVariable Long residentId, @PathVariable Long mentalHealthRecordId) {
        return residentRepository.findById(residentId)
                .map(resident -> {
                    resident.getMentalHealthRecords().removeIf(record -> record.getId().equals(mentalHealthRecordId));
                    residentRepository.save(resident);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
