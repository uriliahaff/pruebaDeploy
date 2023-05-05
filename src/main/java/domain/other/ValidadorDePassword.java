package domain.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collection.*;

public class ValidadorDePassword {
    private List<ComponenteValidador> validaciones = new ArrayList<ComponenteValidador>();

    public List<ComponenteValidador> getValidaciones() {
        return validaciones;
    }

    public void addValidaciones(ComponenteValidador validacion) {
        this.validaciones.add(validacion);
    }
    public void removeValidaciones(ComponenteValidador validacion) {
        this.validaciones.remove(validacion);
    }

    public boolean validarContrasenia(String password)
    {
        return this.validaciones.stream().allMatch(p -> p.validadorContrasenia(password));
    }
}
