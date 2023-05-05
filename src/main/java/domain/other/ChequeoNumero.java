package domain.other;

public class ChequeoNumero implements ComponenteValidador{
    @Override
    public boolean validadorContrasenia(String password) {
        return password.matches(".*\\d.*");
    }
}
