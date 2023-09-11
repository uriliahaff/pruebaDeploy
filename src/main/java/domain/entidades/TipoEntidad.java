package domain.entidades;

import javax.persistence.*;

@Entity
public class TipoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Column
    private String tipo;

    public String getTipo() {
        return tipo;
    }
    public TipoEntidad(String tipo) {
        this.tipo = tipo;
    }
}
