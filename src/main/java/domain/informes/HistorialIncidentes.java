package domain.informes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistorialIncidentes {
    private static HistorialIncidentes instance;
    private List<Incidente> incidentes;

    public HistorialIncidentes() {
        this.incidentes = new ArrayList<>();
    }


    public static HistorialIncidentes getInstance() {
        if (instance == null) {
            instance = new HistorialIncidentes();
        }
        return instance;
    }

    public void agregarIncidente(Incidente incidente) {
        if (!this.incidentes.contains(incidente)) {
            this.incidentes.add(incidente);
        }
    }

}
