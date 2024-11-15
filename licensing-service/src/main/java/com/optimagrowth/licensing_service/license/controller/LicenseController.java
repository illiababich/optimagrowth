package com.optimagrowth.licensing_service.license.controller;

import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.licensing_service.license.model.License;
import com.optimagrowth.licensing_service.license.service.LicenseService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "organization/{organisationId}/license")
@AllArgsConstructor
public class LicenseController {

    private LicenseService licenseService;

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License request, @RequestHeader(value = "Accept-language", required = false) Locale locale) {
        // TODO: add locale
        return ResponseEntity.ok(licenseService.createLicense(request));
    }
    
    @GetMapping(value = "{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organisationId") String organisationId, @PathVariable("licenseId") String licenseId) {
        License license = licenseService.getLicense(licenseId, organisationId);
        license.add(
            linkTo(methodOn(LicenseController.class).getLicense(organisationId, license.getLicenseId())).withSelfRel(),
            linkTo(methodOn(LicenseController.class).createLicense(license, null)).withRel("createLicense"),
            linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
            linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId())).withRel("deleteLicense")
        );

        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License request) {
        
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {

        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }
    
}
