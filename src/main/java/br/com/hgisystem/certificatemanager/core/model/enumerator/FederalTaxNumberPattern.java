package br.com.hgisystem.certificatemanager.core.model.enumerator;

import lombok.Getter;

public enum FederalTaxNumberPattern {
    CPF("###.###.###-##"), CNPJ("##.###.###/####-##");

    @Getter
    private final String value;

    FederalTaxNumberPattern(String value) { this.value = value; }
}
