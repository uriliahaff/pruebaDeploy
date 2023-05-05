package domain.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChequeoDiezMilContrasenias implements ComponenteValidador{

    @Override
    public boolean validadorContrasenia(String password) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("10kpasswords.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (password.equals(line))
                    return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
