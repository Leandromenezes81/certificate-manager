package br.com.hgisystem.certificatemanager.core.message;

public abstract class FederalTaxNumberMessage {
    public static final String EXCEPTION_FEDERAL_TAX_NUMBER_NOT_FOUND = "CNPJ/CPF não encontrado.";
    public static final String EXCEPTION_FEDERAL_TAX_NUMBER_INVALID_CHARACTERS = "CNPJ/CPF podem conter apenas valores numéricos.";
    public static final String EXCEPTION_FEDERAL_TAX_NUMBER_ALREADY_EXISTS = "CNPJ/CPF já existe na base de dados.";
}
