package domain.other;

public class ChequeoMayuscula implements ComponenteValidador{
    @Override
    public boolean validadorContrasenia(String password) {
        return password.matches(".*[A-Z].*");
    }
}
