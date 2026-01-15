package lsdi.IndoorBackend.controllers;

import jakarta.validation.Valid;
import lsdi.IndoorBackend.common.ApiPaths;
import lsdi.IndoorBackend.dtos.CreateOrganizationDTO;
import lsdi.IndoorBackend.services.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(
            OrganizationService organizationService
    ) {
        this.organizationService = organizationService;
    }

    @PostMapping(ApiPaths.ORGANIZATION)
    public ResponseEntity<Long> addOrganization(@Valid @RequestBody CreateOrganizationDTO createOrganizationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.save(createOrganizationDto));
    }

    //TODO: getIdByName
}
