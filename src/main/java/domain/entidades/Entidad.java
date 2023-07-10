package domain.entidades;

public class Entidad {
    private int id;
    private String nombre;
    private String tipo;
    private String email;
    private String descripcion;

    public Entidad(int id, String nombre, String tipo, String email, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.email = email;
        this.descripcion = descripcion;
    }



    // Métodos getter y setter

    public String getNombre(){return nombre;}

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", email='" + email + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
