package domain.services.csvdataloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataLoader {
    public static List<Entidad> leerArchivo() {
        String csvFile = "...a/archivo.csv"; // Ruta del archivo CSV
        List<Entidad> entities = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));

            // Omitir la primera línea del encabezado
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Obtener los campos del archivo CSV
                int id = Integer.parseInt(data[0]);
                String nombre = data[1];
                String tipo = data[2];
                String email = data[3];
                String descripcion = data[4];

                // Crear objeto de entidad
                Entidad entity = new Entidad(id, nombre, tipo, email, descripcion);

                // Agregar entidad a la lista
                entities.add(entity);
            }

            br.close();

            // Procesar la lista de entidades cargadas desde el archivo CSV
            processEntities(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

    // Método para procesar las entidades cargadas
    private static void processEntities(List<Entidad> entidades) {
        // Aquí puedes implementar la lógica para realizar las operaciones necesarias con las entidades cargadas
        // Por ejemplo, almacenar en una base de datos, enviar correos electrónicos, etc.
        for (Entidad entidad : entidades) {
            System.out.println(entidad); // Ejemplo: Imprimir las entidades cargadas
        }
    }
}


