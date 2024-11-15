package com.optimagrowth.licensing_service.license.service;

import java.util.Locale;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.licensing_service.license.config.ServiceConfig;
import com.optimagrowth.licensing_service.license.model.License;
import com.optimagrowth.licensing_service.license.repository.LicenseRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LicenseService {

    private MessageSource messages;
    private LicenseRepository licenseRepository;
    private ServiceConfig config;

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
        
    }

    public License getLicense(String licenseId, String organisationId) {
        License license = licenseRepository.findByOrganisationIdAndLicenseId(organisationId, licenseId);

        if (license == null) {
            throw new IllegalArgumentException(
                String.format(messages.getMessage("license.search.error.message", null, null), licenseId, organisationId)
            );
        }

        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        if (licenseRepository.findByLicenseId(licenseId) != null) {
            licenseRepository.deleteById(licenseId);
            responseMessage = String.format(messages.getMessage("license.delete.success.message", null, null), licenseId);
        } else {
            responseMessage = String.format(messages.getMessage("license.delete.failure.message", null, null), licenseId);
        }

        return responseMessage;
    }
}
