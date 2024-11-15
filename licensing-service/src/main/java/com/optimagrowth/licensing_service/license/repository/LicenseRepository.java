package com.optimagrowth.licensing_service.license.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.optimagrowth.licensing_service.license.model.License;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    public List<License> findByOrganisationId(String organisationId);
    public License findByOrganisationIdAndLicenseId(String organisationId, String licenseId);
    public License findByLicenseId(String licenseId);
}
