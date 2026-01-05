package lsdi.IndoorBackend.domain.model;

import lombok.Getter;

public class CEP {
    @Getter
    private final String value;

    public CEP (String cep) {
        String normalizedCep = cep.replaceAll("\\D", "");

        if (!normalizedCep.matches("\\d{8}")) {
            throw new IllegalArgumentException("Invalid CEP format");
        }

        if (!isValid(normalizedCep)) {
            throw new IllegalArgumentException("Invalid CEP");
        }

        this.value = normalizedCep;
    }

    private boolean isValid(String value) {
        // TODO: validate with api
        return true;
    }

    public String getFormatted() {
        return value.substring(0, 5) + "-" + value.substring(5);
    }
}
