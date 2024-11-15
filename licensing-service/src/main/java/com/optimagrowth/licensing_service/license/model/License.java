package com.optimagrowth.licensing_service.license.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "license")
public class License extends RepresentationModel<License> {
    
    @Id
    @Column(nullable = false)
    private String licenseId;

    private String description;

    @Column(nullable = false)
    private String organisationId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String licenseType;

    private String comment;

    public License withComment(String comment) {
        this.setComment(comment);
        return this;
    }
}
