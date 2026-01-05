package lsdi.IndoorBackend.controllers;

import lsdi.IndoorBackend.common.ApiPaths;
import lsdi.IndoorBackend.dtos.OrganizationDTO;
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
    public ResponseEntity<Long> addOrganization(@RequestBody OrganizationDTO organizationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.save(organizationDto));
    }

    //TODO: getIdByName
}
