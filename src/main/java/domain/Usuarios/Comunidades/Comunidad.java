package domain.Usuarios.Comunidades;

import domain.Usuarios.Usuario;
import domain.localizaciones.Direccion;
import domain.servicios.Servicio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "comunidad")
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "miembro_comunidad",
            joinColumns = @JoinColumn(name = "miembro_id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id")
    )
    private List<Miembro> miembros = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "comunidad_admins",
            joinColumns = @JoinColumn(name = "comunidad_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> admins = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "comunidad_intereses",
            joinColumns = @JoinColumn(name = "comunidad_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<Servicio> intereses = new ArrayList<>();

    @Column(name = "gradoDeConfianza")
    private double gradoDeConfianza;

    // Constructor vacío para Hibernate
    public Comunidad() {
    }

    // Constructor completo
    public Comunidad(String nombre, double gradoDeConfianza) {
        this.nombre = nombre;
        this.gradoDeConfianza = gradoDeConfianza;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getGradoDeConfianza() {
        return gradoDeConfianza;
    }

    public void setGradoDeConfianza(double gradoDeConfianza) {
        this.gradoDeConfianza = gradoDeConfianza;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }
    public void agregarMiembros(Miembro... nuevosMiembros) {
        this.miembros.addAll(Arrays.asList(nuevosMiembros));
    }
    public void agregarInteres(Servicio servicio)
    {
        this.intereses.add(servicio);
    }

    public List<Usuario> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Usuario> admins) {
        this.admins = admins;
    }

    public List<Servicio> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<Servicio> intereses) {
        this.intereses = intereses;
    }


    public void altaMiembro(Miembro miembro)
    {
        miembros.add(miembro);
    }
    public void bajaMiembro(Miembro miembro)
    {
        miembros.remove(miembro);
    }

    public List<Miembro> miembrosFiltradosPorInteresEnLocalizacion(Direccion lugar)
    {
        return miembros.stream()
                .filter(miembro -> miembro.lugarDeInteres(lugar))
                .toList();
    }

    public boolean deInteres(Servicio servicio)
    {
        return intereses.contains(servicio);
    }

    public String getDescripcionConfianza() {
        if (gradoDeConfianza < 2) {
            return "No confiable";
        } else if (gradoDeConfianza >= 2 && gradoDeConfianza < 3) {
            return "Con reservas";
        } else if (gradoDeConfianza >= 3 && gradoDeConfianza < 5) {
            return "Confiable Nivel 1";
        } else {
            return "Confiable Nivel 2";
        }
    }
    public int getGradoDeConfianzaPorDescripcion(String descripcion) {
        switch (descripcion) {
            case "No confiable":
                return 0; // o cualquier valor entre 0 y 2 excluyendo el 2
            case "Con reservas":
                return 2; // o 3, dependiendo de tu necesidad
            case "Confiable Nivel 1":
                return 3; // o cualquier valor entre 3 (excluido) y 5 (incluido)
            case "Confiable Nivel 2":
                return 5; // o cualquier valor mayor que 5
            default:
                throw new IllegalArgumentException("Descripción no válida");
        }
    }



}
