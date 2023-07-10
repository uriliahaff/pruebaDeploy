package domain.servicios;

public class PrestacionDeServicio {
    private Servicio servicio;
    private String localizacion;
    private enum estado{Servicio, FueraDeServicio}

    public Servicio getServicio() {
        return servicio;
    }

    public String getLocalizacion() {
        return localizacion;
    }
}
