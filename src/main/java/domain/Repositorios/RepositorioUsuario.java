package domain.Repositorios;

import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import domain.other.EntityManagerProvider;

import javax.persistence.*;
import java.util.List;

public class RepositorioUsuario {

    private EntityManager entityManager;


    public RepositorioUsuario() {
        this.entityManager = EntityManagerProvider.getInstance().getEntityManager();
    }

    // Métodos generales para guardar y actualizar
    private <T> void saveEntity(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }


    public void actualizar(Object o) {
        EntityTransaction tx = entityManager.getTransaction();
        if(!tx.isActive())
            tx.begin();
        entityManager.merge(o);
        tx.commit();
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
        actualizar(usuario);
    }

    public void saveMiembro(Miembro miembro) {
        saveEntity(miembro);
    }

    public void updateMiembro(Miembro miembro) {
        actualizar(miembro);
    }

    public void saveOrganismoDeControl(OrganismoDeControl organismoDeControl) {
        saveEntity(organismoDeControl);
    }

    public void updateOrganismoDeControl(OrganismoDeControl organismoDeControl) {
        actualizar(organismoDeControl);
    }

    public void saveEntidadPrestadora(EntidadPrestadora entidadPrestadora) {
        saveEntity(entidadPrestadora);
    }

    public void updateEntidadPrestadora(EntidadPrestadora entidadPrestadora) {
        actualizar(entidadPrestadora);
    }

    public void delete(Usuario usuario) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();


            // Intentamos eliminar la entidad asociada correspondiente
            if (
                    tryDeleteEntity(Miembro.class, "Miembro", usuario.getId()) ||
                    tryDeleteEntity(OrganismoDeControl.class, "OrganismoDeControl", usuario.getId()) ||
                    tryDeleteEntity(EntidadPrestadora.class, "EntidadPrestadora", usuario.getId())
            ) {
                // Se eliminó alguna entidad asociada, podemos continuar
            }

            entityManager.remove(usuario);

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

    public List buscarTodosUsuarios() {
        return entityManager.createQuery("from " + Usuario.class.getName()).getResultList();
    }

    public List<Rol> buscarTodosRoles() {
        return entityManager.createQuery("from " + Rol.class.getName()).getResultList();
    }

}
