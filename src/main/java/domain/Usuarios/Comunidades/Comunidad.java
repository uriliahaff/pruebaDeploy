package domain.Usuarios.Comunidades;

import domain.Usuarios.Usuario;
import domain.localizaciones.Direccion;
import domain.servicios.Servicio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Column
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "miembro_comunidad",
            joinColumns = @JoinColumn(name = "miembro_id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id")
    )
    private List<Miembro> miembros;
    @ManyToMany
    @JoinTable(
            name = "comunidad_admins",
            joinColumns = @JoinColumn(name = "comunidad_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> admins;
    @ManyToMany
    @JoinTable(
            name = "comunidad_intereses",
            joinColumns = @JoinColumn(name = "comunidad_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<Servicio> intereses;

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


}
