package br.com.hgisystem.certificatemanager.core.message;

public abstract class DigitalCertificateInfoMessage {
    public static final String REQUIRED_COMPANY_NAME = "Razão social é obrigatória.";
    public static final String REQUIRED_FEDERAL_TAX_NUMBER = "CNPJ/CPF é obrigatório.";
    public static final String REQUIRED_COMPANY_TYPE = "Tipo de empresa é obrigatório.";
    public static final String REQUIRED_ISSUER = "Emissor é obrigatório.";
    public static final String REQUIRED_CERTIFICATE_DATA = "Dados do certificado são obrigatórios";
    public static final String REQUIRED_PASSWORD = "Senha é obrigatória";
    public static final String ERROR_LOAD_CERTIFICATE =
            "Não foi possível carregar o certificado, verifique se o arquivo é um certificado válido e a senha está correta.";
    public static final String REQUIRED_CREATED_AT = "Data de emissão é obrigatória.";
    public static final String REQUIRED_VALID_UNTIL = "Data de validade é obrigatória.";
    public static final String ERROR_INVALID_CERTIFICATE = "Certificado inválido. Verifique a data de expiração e validade.";
}
