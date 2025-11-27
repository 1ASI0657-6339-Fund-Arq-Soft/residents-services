package pe.edu.upc.center.seniorhub.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.DeleteResidentCommand;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetAllResidentsQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByDniQuery;
import pe.edu.upc.center.seniorhub.residents.domain.model.queries.GetResidentByIdQuery;
import pe.edu.upc.center.seniorhub.residents.domain.services.ResidentCommandService;
import pe.edu.upc.center.seniorhub.residents.domain.services.ResidentQueryService;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.CreateResidentResource;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.ResidentDetailsResource;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.resources.ResidentResource;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform.CreateResidentCommandFromResourceAssembler;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform.ResidentResourceFromEntityAssembler;
import pe.edu.upc.center.seniorhub.residents.interfaces.rest.transform.UpdateResidentCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE }, allowCredentials = "false")
@RestController
@RequestMapping(value = "/api/v1/residents", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Residents", description = "Resident Management Endpoints")
public class ResidentsController {

    private final ResidentQueryService residentQueryService;
    private final ResidentCommandService residentCommandService;

    public ResidentsController(ResidentQueryService residentQueryService, ResidentCommandService residentCommandService) {
        this.residentQueryService = residentQueryService;
        this.residentCommandService = residentCommandService;
    }

    @Operation(
            summary = "Create a new resident",
            description = "Register a new resident in the system",
            operationId = "createResident",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resident created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResidentResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RuntimeException.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ResidentResource> createResident(@RequestBody CreateResidentResource resource) {
        var createCommand = CreateResidentCommandFromResourceAssembler.toCommandFromResource(resource);
        var residentId = residentCommandService.handle(createCommand);

        if (residentId == null || residentId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getResidentByIdQuery = new GetResidentByIdQuery(residentId);
        var optionalResident = residentQueryService.handle(getResidentByIdQuery);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var residentResource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return new ResponseEntity<>(residentResource, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all residents",
            description = "Retrieve a list of all residents",
            operationId = "getAllResidents",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of residents retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResidentResource.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ResidentResource>> getAllResidents() {
        var query = new GetAllResidentsQuery();
        var residents = residentQueryService.handle(query);
        var resources = residents.stream()
                .map(ResidentResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(
            summary = "Get a resident by ID",
            description = "Retrieve a resident's details by their ID",
            operationId = "getResidentById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resident found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResidentResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid ID supplied",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resident not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{residentId}")
    public ResponseEntity<ResidentResource> getResidentById(@PathVariable Long residentId) {
        var query = new GetResidentByIdQuery(residentId);
        var optionalResident = residentQueryService.handle(query);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var resource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "Update a resident",
            description = "Update the details of a resident",
            operationId = "updateResident",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resident updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResidentResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{residentId}")
    public ResponseEntity<ResidentResource> updateResident(@PathVariable Long residentId, @RequestBody ResidentResource resource) {
        var updateCommand = UpdateResidentCommandFromResourceAssembler.toCommandFromResource(residentId, resource);
        var optionalResident = residentCommandService.handle(updateCommand);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var updatedResource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return ResponseEntity.ok(updatedResource);
    }

    @Operation(
            summary = "Delete a resident",
            description = "Remove a resident from the system",
            operationId = "deleteResident",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Resident deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid ID supplied",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{residentId}")
    public ResponseEntity<?> deleteResident(@PathVariable Long residentId) {
        var deleteCommand = new DeleteResidentCommand(residentId);
        residentCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get a resident by DNI",
            description = "Retrieve a resident's details by their DNI",
            operationId = "getResidentByDni",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resident found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResidentResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resident not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/searchByDni")
    public ResponseEntity<ResidentResource> getResidentByDni(@RequestParam String dni) {
        var query = new GetResidentByDniQuery(dni);
        var optionalResident = residentQueryService.handle(query);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "Get a resident's details",
            description = "Retrieve detailed information about a resident.",
            operationId = "getResidentDetails",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resident details retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResidentDetailsResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resident not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{residentId}/details")
    public ResponseEntity<ResidentDetailsResource> getResidentDetails(@PathVariable Long residentId) {
        return residentQueryService.handle(new GetResidentByIdQuery(residentId))
                .map(resident -> {
                    var details = new ResidentDetailsResource(
                            resident.getId(),
                            resident.getFullNameAsString(),
                            resident.getDni()
                    );
                    return ResponseEntity.ok(details);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
