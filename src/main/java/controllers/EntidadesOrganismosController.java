package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.entidades.Establecimiento;
import domain.services.csvdataloader.CSVDataLoader;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntidadesOrganismosController {
    private EntityManager entityManager;

    private RepositorioEntidadPrestadoraOrganismoControl repositorioDeEntidadesPrestadoras;


    public EntidadesOrganismosController(RepositorioEntidadPrestadoraOrganismoControl repo){
        this.repositorioDeEntidadesPrestadoras = repo;
    }
    public void indexEntidades(Context context){
        Map<String, Object> model = new HashMap<>();
        List<EntidadPrestadora> entidades = this.repositorioDeEntidadesPrestadoras.buscarTodosEntidades();
        model.put("entidades", entidades);
        model.put("username", context.attribute("username"));

        context.render("cargaEntidades.hbs", model);
    }

    public void indexOrganismos(Context context){
        Map<String, Object> model = new HashMap<>();
        List<OrganismoDeControl> organismos = this.repositorioDeEntidadesPrestadoras.buscarTodosOrganismos();
        model.put("organismos", organismos);

        context.render("cargaOrganismos.hbs", model);
    }

    public void cargarMasivaEntidades(Context context) {
        // CARGA MASIVA
        UploadedFile archivo = context.uploadedFile("archivo");

        if (archivo != null) {
            try (InputStream inputStream = archivo.content()) {
                CSVDataLoader csvDataLoader = new CSVDataLoader();
                List<EntidadPrestadora> entidadesACargar = csvDataLoader.leerArchivo(inputStream);

                // Realizar la lógica de persistencia (guardar en la base de datos, etc.)
                // repositorioDeEntidadesPrestadoras.guardarEntidadesPrestadoras(entidadesACargar);

                context.redirect("/cargaEntidades");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void cargarMasivaOrganismos(Context context) {
        // CARGA MASIVA
        UploadedFile archivo = context.uploadedFile("archivo");

        if (archivo != null) {
            try (InputStream inputStream = archivo.content()) {
                CSVDataLoader csvDataLoader = new CSVDataLoader();
                List<OrganismoDeControl> entidadesACargar = csvDataLoader.leerArchivoOrganismo(inputStream);

                // Realizar la lógica de persistencia (guardar en la base de datos, etc.)
                // repositorioDeEntidadesPrestadoras.guardarEntidadesPrestadoras(entidadesACargar);

                context.redirect("/cargaOrganismos");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void save(EntidadPrestadora entidad) {
        entityManager.getTransaction().begin();
        entityManager.persist(entidad);
        entityManager.getTransaction().commit();
    }



}
