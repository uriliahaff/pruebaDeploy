package domain.Usuarios.Comunidades;

import domain.informes.Incidente;
import domain.services.notificadorDeIncidentes.CommandCuandoSuceden;
import domain.services.notificadorDeIncidentes.CommandSinApuros;
import domain.services.notificadorDeIncidentes.CommandoNotificacion;
import domain.services.notificationSender.ComponenteNotificador;

import javax.persistence.*;
import java.util.List;

@Entity
public class ConfiguracionNotificacionDeIncidentes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String medioPreferido;

    @ElementCollection
    @CollectionTable(name = "horario_preferencia")
    private List<Float> horarioPreferencia;

    @Column
    private float toleranciaEnMinutos = 5.0f;

    public ComponenteNotificador getMedioPreferido() {
        try {
            return (ComponenteNotificador) Class.forName(this.medioPreferido).newInstance();
        } catch (Exception e) {
            // Aquí debes manejar las excepciones adecuadamente
            return null;
        }
    }


    public List<Float> getHorarioPreferencia() {
        return horarioPreferencia;
    }


    public CommandoNotificacion generarComando(Miembro miembro, Incidente incidente)
    {
        if (horarioPreferencia.isEmpty())
        {
            return new CommandCuandoSuceden(miembro, incidente);
        }
        else
        {
            return new CommandSinApuros(miembro, incidente);
        }
    }
}
