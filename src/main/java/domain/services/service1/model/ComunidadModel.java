package domain.services.service1.model;

import domain.Repositorios.RepositorioEstablecimiento;
import domain.Repositorios.RepositorioServicio;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.Usuario;
import domain.entidades.Establecimiento;
import domain.servicios.Servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComunidadModel implements Serializable
{
    public Long id;
    public String nombre;
    public List<MiembroModel> miembros = new ArrayList<>();
    public List<EstablecimientoModel> establecimientosObservados = new ArrayList<>();
    public List<ServicioModel> serviciosObservados = new ArrayList<>();
    public String gradoDeConfianza;
    public ComunidadModel(){}

    public ComunidadModel(Comunidad comunidad) {
        this.id = (long) comunidad.getId(); // Casting int to Long
        this.nombre = comunidad.getNombre();
        // Assuming you have corresponding Model classes for Miembro and Servicio
        for(Miembro miembro : comunidad.getMiembros()) {
            this.miembros.add(new MiembroModel(miembro));
        }
        for(Servicio servicio : comunidad.getIntereses()) {
            this.serviciosObservados.add(new ServicioModel(servicio));
        }

        EstablecimientoModel establecimientoModelMock = new EstablecimientoModel(0L,"Mock");
        this.establecimientosObservados.add(establecimientoModelMock);
        this.gradoDeConfianza = comunidad.getDescripcionConfianza();
    }

    public Comunidad fromModelToEntity(ComunidadModel model, List<Usuario> admins) {
        Comunidad comunidad = new Comunidad();
        //dejo que se genere un id nuevo para la comunidad. No creo que funcione sumar los id de las comunidades
        //comunidad.setId(model.getId());
        comunidad.setNombre(model.getNombre());
        //comunidad.setGradoDeConfianza(Double.parseDouble(model.getGradoDeConfianza()));
        comunidad.setGradoDeConfianza(comunidad.getGradoDeConfianzaPorDescripcion(model.getGradoDeConfianza()));
        List<Integer> miembrosId = model.getMiembros().stream()
                .map(MiembroModel::getId)
                .map(Long::intValue)
                .collect(Collectors.toList());

        List<Miembro> miembros = RepositorioUsuario.findMiembrosByIds(miembrosId);
        comunidad.setMiembros(miembros);
        /*
        List<Integer> establecimientosId = model.getEstablecimientos().stream()
                .map(EstablecimientoModel::getId)
                .map(Long::intValue)
                .collect(Collectors.toList());

        List<Establecimiento> establecimientos = RepositorioEstablecimiento.findEstablecimientoByIds(establecimientosId);
        comunidad.setMiembros(miembros);*/
        List<Integer> servicioId = model.getServicios().stream()
                .map(ServicioModel::getId)
                .map(Long::intValue)
                .collect(Collectors.toList());

        List<Servicio> servicios = RepositorioServicio.findServicioByIds(servicioId);
        comunidad.setIntereses(servicios);

        comunidad.setAdmins(admins);

        return comunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public List<MiembroModel> getMiembros() {
        return miembros;
    }

    public List<EstablecimientoModel> getEstablecimientos() {
        return establecimientosObservados;
    }

    public List<ServicioModel> getServicios() {
        return serviciosObservados;
    }

    public String getGradoDeConfianza() {
        return gradoDeConfianza;
    }
}

