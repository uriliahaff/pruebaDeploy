package domain.services.service2.model;

import domain.informes.Incidente;
import domain.services.service2.model.UsuarioModel;

public class IncidenteModel
{
    private Integer prestacionDeServicio;
    private String apertura;
    private String cierre;
    private UsuarioModel usuarioApertura;
    private UsuarioModel usuarioAnalizador;

    public IncidenteModel(){}
    public IncidenteModel(Incidente incidente)
    {
        this.prestacionDeServicio = incidente.getServicioAfectado().getId();
        this.apertura = incidente.getFechaInicio().toString();
        this.cierre = incidente.getFechaCierre().toString();
        this.usuarioApertura = new UsuarioModel(incidente.getMiembroInformante().getUsuario());
        this.usuarioAnalizador = new UsuarioModel(incidente.getMiembroAnalizador().getUsuario());
    }

}
