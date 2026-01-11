package lsdi.IndoorBackend.controllers;

import jakarta.validation.Valid;
import lsdi.IndoorBackend.common.ApiPaths;
import lsdi.IndoorBackend.dtos.InferenceIndoorEnvironmentDTO;
import lsdi.IndoorBackend.dtos.OrganizationDTO;
import lsdi.IndoorBackend.dtos.OrganizationHierarchyLocationDTO;
import lsdi.IndoorBackend.services.InferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.INFERENCE)
public class InferenceController {

    private final InferenceService inferenceService;

    public InferenceController(InferenceService inferenceService) {
        this.inferenceService = inferenceService;
    }

    @PostMapping(ApiPaths.OFFLINE)
    public ResponseEntity<String> addEnvironmentIndoorTrainingData(@RequestBody @Valid OrganizationDTO organizationDTO) {
        inferenceService.saveOrganizationIndoorTraining(organizationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationDTO> getOrganizationIndoorTrainingData(
            @PathVariable Long organizationId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                inferenceService.getIndoorInferenceById(organizationId
        ));
    }

    @PostMapping(ApiPaths.ONLINE)
    public void addIndoorInference(InferenceIndoorEnvironmentDTO inferenceDTO) {
        inferenceService.addInference(inferenceDTO);
        ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping(ApiPaths.ONLINE+"/{userId}")
    public ResponseEntity<OrganizationHierarchyLocationDTO> getUserLastLocation(@PathVariable Long userId) {
        OrganizationHierarchyLocationDTO organizationHierarchyLocationDTO = OrganizationHierarchyLocationDTO
                .fromDomain(inferenceService.getUserLastLocation(userId));

        return ResponseEntity.status(HttpStatus.OK).body(organizationHierarchyLocationDTO);
    }
}
