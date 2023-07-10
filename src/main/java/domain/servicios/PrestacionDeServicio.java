package domain.servicios;

import domain.localizaciones.Establecimiento;

public class PrestacionDeServicio {
    private Servicio servicio;
    private Establecimiento establecimiento;


    public Servicio getServicio() {
        return servicio;
    }

    public Establecimiento getLocalizacion() {
        return establecimiento;
    }
}
