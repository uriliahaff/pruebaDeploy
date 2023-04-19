package domain.humanos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.*;

public class Usuario {
    private String username;
    private String password;
    private Persona persona;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean cumpleLongitud(String password){
        return password.length() >= 12 ;
    }

    public boolean cumpleComplejidad(String password){
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).+$";

        return password.matches(regex);
    }

    public boolean cumplePoliticasDeContrasenas(String password){
        return cumpleLongitud(password) && cumpleComplejidad(password);
    }

    public boolean cumpleOWASP(String password){
        return (isPasswordInFile(password)
                && cumplePoliticasDeContrasenas(password));
    }

}
