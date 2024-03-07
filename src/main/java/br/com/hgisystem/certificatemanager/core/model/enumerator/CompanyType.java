package br.com.hgisystem.certificatemanager.core.model.enumerator;

import lombok.Getter;

public enum CompanyType {
    PESSOA_FISICA("F"), PESSOA_JURIDICA("J");

    @Getter
    private final String value;

    CompanyType(String value) {
        this.value = value;
    }
}
