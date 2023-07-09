package domain.repositorios;

import domain.Usuarios.Comunidades.Comunidad;

import java.util.ArrayList;
import java.util.List;

public class RepositorioComunidades {
    private static RepositorioComunidades instancia;
    private List<Comunidad> comunidades;

    // Constructor privado para evitar la creación de instancias desde fuera de la clase
    private RepositorioComunidades() {
        comunidades = new ArrayList<>();
        // Inicialización del repositorio de comunidades
    }

    // Método estático para obtener la única instancia del repositorio
    public static RepositorioComunidades getInstance() {
        if (instancia == null) {
            instancia = new RepositorioComunidades();
        }
        return instancia;
    }
    public List<Comunidad> obtenerComunidades() {
        return comunidades;
    }
    // Métodos y funcionalidades del repositorio
}
