package domain.other;

public class ChequeoLongitud implements ComponenteValidador {
    @Override
    public boolean validadorContrasenia(String password) {  return password.length()>=12;   }
}
