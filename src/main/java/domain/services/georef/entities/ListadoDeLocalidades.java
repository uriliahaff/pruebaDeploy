package domain.services.georef.entities;

import java.util.List;

public class ListadoDeLocalidades {
    public int cantidad;
    public int inicio;
    public int total;
    public Parametro parametros;
    public List<Localidad> localidades;
    private class Parametro {
        public List<String> campos;
        public int max;
        public List<String> provincia;
    }

}
