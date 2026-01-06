package lsdi.IndoorBackend.controllers;

import jakarta.validation.Valid;
import lsdi.IndoorBackend.common.ApiPaths;
import lsdi.IndoorBackend.dtos.IndoorInferenceDTO;
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

    @PostMapping
    public ResponseEntity<String> addEnvironmentIndoorInferences(@RequestBody @Valid IndoorInferenceDTO regionFingerprints) {
        inferenceService.addInferences(regionFingerprints);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<IndoorInferenceDTO> getIndoorInference(
            @PathVariable Long organizationId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                inferenceService.getIndoorInferenceById(organizationId
        ));
    }
}
