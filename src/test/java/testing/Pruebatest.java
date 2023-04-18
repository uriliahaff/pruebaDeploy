package testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


import java.io.*;

public class Pruebatest {
    @Test
    public void passwordInsegura()
    {
        Assertions.assertFalse(isPasswordInFile("123456"));
    }
    @Test
    public void passwordSegura()
    {
        Assertions.assertTrue(isPasswordInFile("12345g6"));
    }
    public boolean isPasswordInFile(String password) {
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
