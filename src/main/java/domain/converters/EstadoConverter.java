package domain.converters;

import domain.servicios.Estado;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EstadoConverter implements AttributeConverter<Estado, String> {

    @Override
    public String convertToDatabaseColumn(Estado estado) {
        return null;
    }

    @Override
    public Estado convertToEntityAttribute(String s) {
        return null;
    }


}
