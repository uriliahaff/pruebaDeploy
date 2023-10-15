package domain.Repositorios;

import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.Usuarios.Usuario;
import domain.other.EntityManagerProvider;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

public class RepositorioUsuario {

    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();;

    /*public RepositorioUsuario() {
        this.entityManager = EntityManagerProvider.getInstance().getEntityManager();
    }*/

    // Métodos generales para guardar y actualizar
    private <T> void saveEntity(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }

    private <T> void updateEntity(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
    }


    public <T> T findById(Class<T> clazz, int id) {
        return entityManager.find(clazz, id);
    }
    public Usuario findUsuarioById(int id) {
        return entityManager.find(Usuario.class, id);
    }
    public Usuario findUsuarioByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Manejar el caso en que no se encuentra ningún usuario con el nombre de usuario dado
            return null;
        }    }

    public Miembro findMiembroById(int id) {
        return entityManager.find(Miembro.class, id);
    }

    public OrganismoDeControl findOrganismoDeControlById(int id) {
        return entityManager.find(OrganismoDeControl.class, id);
    }

    public EntidadPrestadora findEntidadPrestadoraById(int id) {
        return entityManager.find(EntidadPrestadora.class, id);
    }

    // Métodos específicos que usan los métodos generales
    public void saveUsuario(Usuario usuario) {
        saveEntity(usuario);
    }

    public void updateUsuario(Usuario usuario) {
        updateEntity(usuario);
    }

    public void saveMiembro(Miembro miembro) {
        saveEntity(miembro);
    }

    public void updateMiembro(Miembro miembro) {
        updateEntity(miembro);
    }

    public void saveOrganismoDeControl(OrganismoDeControl organismoDeControl) {
        saveEntity(organismoDeControl);
    }

    public void updateOrganismoDeControl(OrganismoDeControl organismoDeControl) {
        updateEntity(organismoDeControl);
    }

    public void saveEntidadPrestadora(EntidadPrestadora entidadPrestadora) {
        saveEntity(entidadPrestadora);
    }

    public void updateEntidadPrestadora(EntidadPrestadora entidadPrestadora) {
        updateEntity(entidadPrestadora);
    }

    public void delete(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario != null) {

            // Intentamos eliminar la entidad asociada correspondiente
            if (
                    tryDeleteEntity(Miembro.class, "Miembro", id) ||
                    tryDeleteEntity(OrganismoDeControl.class, "OrganismoDeControl", id) ||
                    tryDeleteEntity(EntidadPrestadora.class, "EntidadPrestadora", id)
            ) {
                // Se eliminó alguna entidad asociada, podemos continuar
            }

            entityManager.remove(usuario);
        }

        transaction.commit();
    }

    private <T> boolean tryDeleteEntity(Class<T> clazz, String className, int userId) {
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + className + " e WHERE e.usuario.id = :id", clazz);
        query.setParameter("id", userId);
        T entity = query.getResultStream().findFirst().orElse(null);
        if (entity != null) {
            entityManager.remove(entity);
            return true;
        }
        return false;
    }
    public static List<Miembro> findMiembrosByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        TypedQuery<Miembro> query = entityManager.createQuery(
                "SELECT u FROM Miembro u WHERE u.id IN :ids", Miembro.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }

}
