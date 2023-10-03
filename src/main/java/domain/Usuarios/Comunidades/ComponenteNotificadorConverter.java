package domain.Usuarios.Comunidades;
import domain.services.notificationSender.ComponenteNotificador;
import domain.services.notificationSender.NotificarViaCorreo;
import domain.services.notificationSender.NotificarViaWpp;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ComponenteNotificadorConverter implements AttributeConverter<ComponenteNotificador, String> {

    @Override
    public String convertToDatabaseColumn(ComponenteNotificador medio) {
        if (medio instanceof NotificarViaCorreo) {
            return "CORREO";
        } else if (medio instanceof NotificarViaWpp) {
            return "WPP";
        }
        return null;
    }

    @Override
    public ComponenteNotificador convertToEntityAttribute(String codigo) {
        if ("CORREO".equals(codigo)) {
            return new NotificarViaCorreo();
        } else if ("WPP".equals(codigo)) {
            return new NotificarViaWpp();
        }
        return null;
    }
}
