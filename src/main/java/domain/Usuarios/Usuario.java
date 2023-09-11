package domain.Usuarios;

import domain.other.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

    @Transient
    private ValidadorDePassword vp = new ValidadorDePassword();
    //TODO: Disccutir como correjir esto


    public List<Rol> getRoles() {
        return roles;
    }

    public Usuario() {
        vp.addValidaciones(new ChequeoDiezMilContrasenias());
        vp.addValidaciones(new ChequeoMayuscula());
        vp.addValidaciones(new ChequeoLongitud());
        vp.addValidaciones(new ChequeoCaracterEspecial());
        vp.addValidaciones(new ChequeoNumero());
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        roles = new ArrayList<>();

    }
    public void addRol(Rol rol)
    {
        this.roles.add(rol);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean cumpleOWASP(String password){
        return this.vp.validarContrasenia(password);
    }

}
