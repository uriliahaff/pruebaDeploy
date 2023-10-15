package domain.services.csvdataloader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import domain.Repositorios.RepositorioEntidad;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.Usuarios.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVDataLoader {
    public List<EntidadPrestadora> leerArchivoEntidades(InputStream inputStream) {
        List<EntidadPrestadora> entidadesPrestadoras = new ArrayList<>();
        RepositorioEntidad repositorioEntidad = new RepositorioEntidad();
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> records = csvReader.readAll();

            boolean primeraLinea = true;
            for (String[] record : records) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                // Aquí debes mapear los campos del archivo CSV a los atributos de EntidadPrestadora
                int entidad_id = Integer.parseInt(record[0]);
                int usuario_id = Integer.parseInt(record[1]);
                String mail = record[2];
                String nombre = record[3];
                String descripcion = record[4];
                // Mapea los demás campos...

                // Crea un objeto EntidadPrestadora y agrégalo a la lista
                EntidadPrestadora entidadPrestadora = new EntidadPrestadora();
               entidadPrestadora.setEntidad(repositorioEntidad.findEntidadById(entidad_id));
                entidadPrestadora.setUsuario(repositorioUsuario.findUsuarioById(usuario_id));
                entidadPrestadora.setNombre(nombre);
                entidadPrestadora.setDescripcion(descripcion);
                entidadPrestadora.setCorreoElectronicoResponsable(mail);
                // Asigna los demás campos...

                entidadesPrestadoras.add(entidadPrestadora);
            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error al leer el archivo CSV", e);
        }


        return entidadesPrestadoras;

    }

    public List<OrganismoDeControl> leerArchivoOrganismo(InputStream inputStream) {
        List<OrganismoDeControl> organismosControl = new ArrayList<>();


        // CODIGO ACA


        return organismosControl;

    }
}

