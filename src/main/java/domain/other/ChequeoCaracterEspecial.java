package domain.other;

public class ChequeoCaracterEspecial implements ComponenteValidador{
    @Override
    public boolean validadorContrasenia(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}
