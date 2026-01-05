package lsdi.IndoorBackend.entities.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lsdi.IndoorBackend.domain.model.CEP;

@Converter(autoApply = false)
public class CEPConverter implements AttributeConverter<CEP, String> {

    @Override
    public String convertToDatabaseColumn(CEP cep) {
        return cep == null ? null : cep.getValue();
    }

    @Override
    public CEP convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : new CEP(dbValue);
    }
}
