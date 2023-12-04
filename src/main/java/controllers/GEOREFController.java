package controllers;

import domain.Repositorios.RepositorioDireccion;
import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioUsuario;
import domain.services.georef.ServicioGeoref;
import domain.services.georef.entities.*;
import io.javalin.http.Context;

import java.io.IOException;

public class GEOREFController {

    private RepositorioDireccion repoGEOREF;

    public GEOREFController(RepositorioDireccion repoGEOREF){
        this.repoGEOREF = repoGEOREF;
    }
    public void index(Context context){

        context.render("georef.hbs");
    }

    public void actualizar(Context context) throws IOException {
        ServicioGeoref servicio = ServicioGeoref.getInstancia();
        ListadoDeProvincias provinciasNuevas = servicio.listadoDeProvincias();

        for(Provincia provinciaNueva:provinciasNuevas.provincias){

            ListadoDeMunicipios municipiosNuevos = servicio.listadoDeMunicipiosDeProvincia(provinciaNueva.getId());
            //ListadoDeLocalidades localidadesNuevas = servicio.listadoDeLocalidadesDeProvincia(provinciaNueva.getId()); // NOSE porque no funciona

            for(Municipio municipioNuevo: municipiosNuevos.municipios){

                //repoGEOREF.saveMunicipio(municipioNuevo); // ERROR ACA "detached"

            }

            /*for(Localidad localidadNueva: localidadesNuevas.localidades){

            }*/

          //  repoGEOREF.saveProvincia(provinciaNueva); // ERROR ACA "detached"
        }

        context.redirect("/georef");
    }

    public void limpiarDatos(){

    }



}
