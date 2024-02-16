package br.com.tiviatest.domain.enums;

public enum TipoDocumento {
    CPF("CPF"),
    RG("RG"),
    CNPJ("CNPJ"),
    CNH("CNH");

    private final String documento;

    TipoDocumento(String documento) {
        this.documento = documento;
    }

    public String getDocumento() {
        return documento;
    }
}
