package domain.Usuarios.Comunidades;

import domain.informes.Incidente;
import domain.services.notificadorDeIncidentes.CommandCuandoSuceden;
import domain.services.notificadorDeIncidentes.CommandSinApuros;
import domain.services.notificadorDeIncidentes.CommandoNotificacion;
import domain.services.notificationSender.ComponenteNotificador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ConfiguracionNotificacionDeIncidentes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = ComponenteNotificadorConverter.class)
    private ComponenteNotificador medioPreferido;

    @ElementCollection
    @CollectionTable(name = "horario_preferencia")
    private List<Float> horarioPreferencia = new ArrayList<>();

    @Column
    private float toleranciaEnMinutos = 5.0f;

    public void setMedioPreferido(ComponenteNotificador medioPreferido) {
        this.medioPreferido = medioPreferido;
    }

    public ComponenteNotificador getMedioPreferido() {
        /*try {
            return (ComponenteNotificador) Class.forName(this.medioPreferido).newInstance();
        } catch (Exception e) {
            // Aquí debes manejar las excepciones adecuadamente
            return null;
        }*/
        return this.medioPreferido;
    }

    public ConfiguracionNotificacionDeIncidentes() {
    }

    public ConfiguracionNotificacionDeIncidentes(ComponenteNotificador medioPreferido) {
        this.medioPreferido = medioPreferido;
    }

    public List<Float> getHorarioPreferencia() {
        return horarioPreferencia;
    }


    public CommandoNotificacion generarComando(Miembro miembro, Incidente incidente)
    {
        if (horarioPreferencia == null || horarioPreferencia.isEmpty())
        {
            return new CommandCuandoSuceden(miembro, incidente);
        }
        else
        {
            return new CommandSinApuros(miembro, incidente);
        }
    }
}
