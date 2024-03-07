package br.com.hgisystem.certificatemanager.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FederalTaxInfo {
    private String federalTaxNumber;
    private String companyType;
}
