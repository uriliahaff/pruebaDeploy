package domain.Repositorios;

import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioEntidadPrestadoraOrganismoControl {

    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public List buscarTodosEntidades() {
        return entityManager.createQuery("from " + EntidadPrestadora.class.getName()).getResultList();
    }

    public List buscarTodosOrganismos() {
        return entityManager.createQuery("from " + OrganismoDeControl.class.getName()).getResultList();
    }



}
