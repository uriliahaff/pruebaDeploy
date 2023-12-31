# Proyecto

<h3 align="center" >
  Monitoreo de Estado de Servicios de Transporte Público y de Establecimientos. 
  <br> <br> <br>
  Sistema de Apoyo a Comunidades con Movilidad Reducida 
  <br> <br>
</h3>

<p align ="center">  Trabajo Práctico Anual Integrador
  <br>
  -2023-
 </p>

<h3 align="center">Contexto general</h3>

<h4 style="color: lightgrey;">Problematica</h4>

<p>Las personas con movilidad reducida (permanente o temporal) a menudo enfrentan desafíos significativos en términos de movilidad en una ciudad. Además debemos considerar a las personas que llevan niños pequeños que también pueden verse afectados. <br> </p>

<p>Algunos de los problemas comunes de movilidad incluyen: </p>

<p><strong>Accesibilidad:</strong> Muchas ciudades no están diseñadas para ser accesibles. Las aceras estrechas, los obstáculos en las calles y la falta de rampas para sillas de ruedas pueden dificultar que las personas con movilidad reducida se desplacen de manera segura y efectiva. </p> 

<p><strong>Transporte público:</strong> Los autobuses y trenes pueden ser difíciles de subir y bajar, y pueden no estar equipados con rampas o elevadores. Incluso si el transporte público es accesible, puede ser difícil para las personas con discapacidades físicas encontrar información sobre rutas y horarios. </p>

<p><strong>Edificios inaccesibles:</strong> Las instalaciones de los edificios en las ciudades pueden tener escaleras y puertas estrechas, y los baños pueden ser inaccesibles o no estar disponibles. </p>

<p>Para moverse a través de las ciudades, estas personas suelen investigar a los lugares donde concurrirán pero muchas veces la información hallada no concuerda con la realidad. Por ejemplo, Martina necesita llevar a su bebé de 3 meses al pediatra que queda en la estación de Facultad de Medicina de la línea D de Subte de Buenos Aires. Al bajar en dicha estación se encuentra con que el ascensor no funciona y por lo tanto debe pedir ayuda para subir la escalera con el cochecito.</p>

<p>La situación mencionada en el párrafo anterior, y otras que seguramente el lector está visualizando, son las que trabajaremos en el presente trabajo práctico.</p>

<h4 style="color: lightgrey;">Nuestro Sistema</h4>

<p>A partir de la problemática identificada y presentada en la sección anterior, se presentan los requerimientos para el diseño y construcción de un Sistema de Monitoreo de Estado de Servicios.</p>

<h4 style="color: lightgrey;">Las entregas</h4>

<p>Serán 7 entregas, algunas orientadas específicamente a la inclusión de funcionalidades, mientras que otras se abocarán a la inclusión de algunos aspectos del diseño y herramientas tecnológicas para la implementación del mismo.</p>

<p>Las entregas previstas se muestran a continuación, aunque pueden sufrir algunas modificaciones en su alcance o fechas:</p>

| Nro | Titulo | Fecha de entrega |
| --- | ------ | ---------------- |
| 1   | [Modelado en Objetos – Parte I: Puesta a punto del entorno de desarrollo y primera iteración del diseño](https://github.com/dds-utn/2023-tpa-mama-grupo-17#modelado-en-objetos--parte-i-puesta-a-punto-del-entorno-de-desarrollo-y-primera-iteraci%C3%B3n-del-dise%C3%B1o) | 25-04-2023      |
| 2   | [Modelado en Objetos – Parte II: Incrementando funcionalidades]() | 29/5 al 2 de junio      |
| 3   | Modelado en Objetos – Parte III: Incrementando funcionalidades | 3 al 8 de julio      |
| 4   | Servicios y Persistencia de Datos | 21 al 25 de agosto      |
| 5   | Servicios y Maquetado Web | 25 al 30 de septiembre   |
| 6   | Arquitectura Web con Cliente Pesado | 23 al 28 de octubre     |
| 7   | Arquitectura Web con Cliente Liviano | 20 al 25 de noviembre       |

## Modelado en Objetos – Parte I: Puesta a punto del entorno de desarrollo y primera iteración del diseño 

### Entregables requeridos

#### Modelo de Casos de Uso

> Requerimientos generales
> 1. Se debe permitir la administración de servicios públicos (en adelante se llama “administración” a las acciones de alta, baja y modificación)
> 2. Se debe permitir la administración de servicios
> 3. Se debe permitir la administración de prestación de servicios
> 4. Se debe permitir la administración de comunidades y miembros

> Requerimientos de seguridad
> - El sistema debe permitir el registro de usuarios. Por el momento sólo se requiere guardar usuario y contraseña.


<p align="center">
  <img src="https://github.com/dds-utn/2023-tpa-mama-grupo-17/blob/main/Entregas/Entrega%201/cu.png?raw=true" alt="Modelo de Casos de Uso" />
</p>
 🔗🖼️<a href="https://github.com/dds-utn/2023-tpa-mama-grupo-17/tree/main/Entregas/Entrega%201"> Imagen </a>




#### Modelo de Objetos
<p align="center">
  <img src="https://github.com/dds-utn/2023-tpa-mama-grupo-17/blob/main/Entregas/Entrega%201/dd.png?raw=true" alt="Modelo de Casos de Uso" />
</p>
 🔗🖼️<a href="https://github.com/dds-utn/2023-tpa-mama-grupo-17/tree/main/Entregas/Entrega%201"> Diagrama </a>

#### Documento con las decisiones de diseño tomadas y su justificación

 🔗🗎<a href="https://github.com/dds-utn/2023-tpa-mama-grupo-17/blob/main/Entregas/Entrega%201/Justificacion.pdf"> Documento </a>


#### Implementacion
> Siguiendo las recomendaciones del OWASP (Proyecto Abierto de Seguridad en Aplicaciones Web), que se ha constituido en un estándar de facto para la seguridad, se pide:
> - No utilice credenciales por defecto en su software, particularmente en el caso de administradores. 
> - Implemente controles contra contraseñas débiles. Cuando el usuario ingrese una nueva clave, la misma puede verificarse contra la lista del Top 10.000 de peores contraseñas.
> - Alinear la política de longitud, complejidad y rotación de contraseñas con las recomendaciones de la Sección 5.1.1.2 para Secretos Memorizados de la Guía NIST 800-63.
> - Limite o incremente el tiempo de respuesta de cada intento fallido de inicio de sesión

Los siguientes metodos pertenecen a la clase ```Usuario() ```

```java
    public boolean cumpleOWASP(String password){
        return (isPasswordInFile(password)
                && cumplePoliticasDeContrasenas(password));
    }
```

```java
    public boolean cumplePoliticasDeContrasenas(String password){
        return cumpleLongitud(password) && cumpleComplejidad(password);
    }
```
```java
    public boolean cumpleComplejidad(String password){
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).+$";

        return password.matches(regex);
    }
```
```java
    public boolean cumpleLongitud(String password){
        return password.length() >= 12 ;
    }
```
```java
    public boolean isPasswordInFile(String password) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("10kpasswords.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (password.equals(line))
                    return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
```
*_Se utiliza el documento 🔗🗎 [10kpasswords.txt](../main/src/main/resources/10kpasswords.txt) que contiene las la lista del Top 10.000 de peores contraseñas para comparar_

## Modelado en Objetos – Parte II: Incrementando funcionalidades

### Entregables requeridos

#### Modelo de Casos de Uso
> Requerimientos generales
> 1. Se debe permitir la administración de entidades
> 2. Se debe permitir la administración de establecimientos
> 3. Se debe permitir la asignación de personas a servicios de interés
> 4. Se debe permitir la asociación de localizaciones a personas
> 5. Se debe permitir la asociación de localizaciones a entidades
> 6. Se debe permitir la administración de entidades prestadoras y organismos de control

<p align="center">
  <img src="https://github.com/dds-utn/2023-tpa-mama-grupo-17/blob/main/Entregas/Entrega%202/CU.png" alt="Modelo de Casos de Uso" />
</p>
 🔗🖼️<a href="https://github.com/dds-utn/2023-tpa-mama-grupo-17/tree/main/Entregas/Entrega%202"> Imagen </a>

#### Modelo de Objetos
<p align="center">
  <img src="https://github.com/dds-utn/2023-tpa-mama-grupo-17/blob/main/Entregas/Entrega%202/ddo.png" alt="Modelo de Objetos" />
</p>
 🔗🖼️<a href="https://github.com/dds-utn/2023-tpa-mama-grupo-17/tree/main/Entregas/Entrega%202"> Diagrama </a>

#### Implementación de la carga masiva de datos de entidades prestadoras y organismos de control
> Dado que uno de los objetivos del Sistema es ayudar a mejorar la calidad de los servicios públicos, en esta versión se incorporan como usuarios de la plataforma a las empresas o entidades propietarias de los servicios públicos y a los organismos de control (en caso de que existiese por el tipo de servicio). Cada empresa podrá designar una persona a la cual le llegará información resumida sobre las problemáticas de los servicios que se ofrecen. De igual manera, los organismos de control podrán designar una persona con el mismo objetivo. La generación de la información que recibirán estará a cargo de un servicio de software específico que será detallado en la próxima entrega.
> 
> La carga de datos de entidades prestadoras y organismos de control debe poder ser realizada en forma masiva a través de la carga de un archivo CSV.


Los siguientes metodos pertenecen a la clase ```CSVDataLoader() ```

```java
   public static List<OrganizacionExterna> leerArchivo() {
        String csvFile = "...a/archivo.csv"; // Ruta del archivo CSV
        List<OrganizacionExterna> entities = new ArrayList<>();

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

              // Crear objeto de entidad según el tipo
              if (tipo.equals("Entidad Prestadora")) {
                  EntidadPrestadora entity = new EntidadPrestadora(id, nombre, tipo, email, descripcion);
                  entities.add(entity);
              }   else if (tipo.equals("Organismo de Control")) {
                 OrganismoDeControl entity = new OrganismoDeControl(id, nombre, tipo, email, descripcion);
                  entities.add(entity);
              }

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
```
#### Implementación de la integración con el servicio GeoRef API
> Las localizaciones posibles de ser asignadas a las personas y a las entidades deben ser obtenidas del servicio GeoRef API (API del Servicio de Normalización de Datos Geográficos de Argentina) de la plataforma de datos abiertos del Gobierno Nacional Argentino. 
<p align="center">
  <img src="https://github.com/dds-utn/2023-tpa-mama-grupo-17/blob/main/Entregas/Entrega%202/GeoRef.png" alt="Modelo de Objetos" />
</p>

Clase ```Localizacion() ```

```java
public class Localizacion {
    private ILocalizacionAdapter localizacionAdapter;
    private String nombre;
    public LocalizacionService() {
        this.localizacionService = new GeoRefAdapter();
    }

   public List<Provincia> obtenerListadoDeProvincias() {
        return this.localizacionAdapter.obtenerListadoDeProvincias();
    }

    public List<Municipio> obtenerMunicipiosDeProvincia(Provincia provincia) {
        return this.localizacionAdapter.obtenerMunicipiosDeProvincia(provincia);
    }
}

```
Interfaz ```ILocationAdapter ```

```java
public interface GeoRefAdapter {
  ListadoDeProvincias listadoDeProvincias();
  ListadoDeMunicipios listadoDeMunicipiosDeProvincia(int id);
}

```

Clase ```GeoRefConcreteAdapter() ```

```java
  public class GeoRefConcreteAdapter implements ILocationAdapter {
    private static GeoRefAdapter instacia = null;
    private static final String urlAPI = "https://apis.datos.gob.ar/georef/api/";
    private Retrofit retrofit;
    
    private ServicioGeoref(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    
    public static ServicioGeoref getInstancia(){
        if(instacia==null){
            instacia = new ServicioGeoref();
        }
        return instacia;
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
        GeoRefAdapter georefService = this.retrofit.create(GeoRefAdapter.class);
        Call<ListadoDeProvincias> requestListadoProvinciasArg = georefService.provincias();
        Response<ListadoDeProvincias> responseListadoProvinciasArg = requestListadoProvinciasArg.execute();
        return responseListadoProvinciasArg.body();
    }

    public ListadoDeMunicipios listadoDeMunicipiosDeProvincia(int id) throws IOException {
        GeoRefAdapter georefService = this.retrofit.create(GeoRefAdapter.class);
        Call<ListadoDeMunicipios> requestListadoMunicipiosDeProvincia = georefService.municipios(id);
        Response<ListadoDeMunicipios> responseListadoMunicipiosDeProvincia = requestListadoMunicipiosDeProvincia.execute();
        return responseListadoMunicipiosDeProvincia.body();
    }

}
```
Interfaz ```GeoRefAdapter() ```

```java
public interface GeorefService {
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("campos")String campos);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia")int idProvincia);

}
```


Depedencias 

```xml
  <dependencies>
    <dependency>
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>converter-gson</artifactId>
      <version>2.9.0</version>
    </dependency>
  </dependencies>

```

#### Documento con diseño archivo csv
```csv
ID,Nombre,Tipo,Email,Descripción

```
Ejemplo:
```csv
"ID,Nombre,Tipo,Email,Descripción"
"1,Telecom Argentina,Entidad Prestadora,info@telecom.com.ar,""Telecom Argentina es una empresa líder en servicios de telecomunicaciones en Argentina. Ofrece soluciones de Internet, telefonía fija y móvil, así como televisión por cable. Su objetivo es brindar conectividad confiable y de calidad a sus clientes en todo el país."""
"2,Edesur,Entidad Prestadora,atencionalcliente@edesur.com.ar,""Edesur es una empresa distribuidora de energía eléctrica en la zona sur de la Ciudad de Buenos Aires y el Gran Buenos Aires. Provee servicio a millones de clientes residenciales, comerciales e industriales."""
"3,Ente Nacional de Comunicaciones (ENACOM),Organismo de Control,info@enacom.gob.ar,""El Ente Nacional de Comunicaciones (ENACOM) es el organismo de control encargado de regular las comunicaciones en Argentina. Su misión es promover la competencia y el acceso equitativo a servicios de telecomunicaciones en todo el país."""
"4,YPF,Entidad Prestadora,ypf@ypf.com.ar,""YPF es una empresa argentina líder en la industria de hidrocarburos. Se dedica a la exploración, producción, refinación y comercialización de petróleo, gas natural y sus derivados. También ofrece servicios en estaciones de servicio y lubricantes."""
"5,Metrovías,Entidad Prestadora,atencionpasajeros@metrovias.com.ar,""Metrovías es una empresa encargada de operar el servicio de trenes y subtes en Buenos Aires. Brinda transporte público a millones de pasajeros en la ciudad y sus alrededores, asegurando la movilidad eficiente y segura."""
```

#### Documento con las decisiones de diseño tomadas y su justificación

 🔗🗎<a href="https://github.com/dds-utn/2023-tpa-mama-grupo-17/blob/main/Entregas/Entrega%202/Justificacion.pdf"> Documento </a>

# java-base-project

Esta es una plantilla de proyecto diseñada para: 

* Java 17. :warning: Si bien el proyecto no lo limita explícitamente, el comando `mvn verify` no funcionará con versiones más antiguas de Java. 
* JUnit 5. :warning: La versión 5 de JUnit es la más nueva del framework y presenta algunas diferencias respecto a la versión "clásica" (JUnit 4). Para mayores detalles, ver: 
  *  [Apunte de herramientas](https://docs.google.com/document/d/1VYBey56M0UU6C0689hAClAvF9ILE6E7nKIuOqrRJnWQ/edit#heading=h.dnwhvummp994)
  *  [Entrada de Blog (en inglés)](https://www.baeldung.com/junit-5-migration) 
  *  [Entrada de Blog (en español)](https://www.paradigmadigital.com/dev/nos-espera-junit-5/)
* Maven 3.8.1 o superior

## Ejecutar tests

```
mvn test
```

## Validar el proyecto de forma exahustiva

```
mvn clean verify
```

Este comando hará lo siguiente:

 1. Ejecutará los tests
 2. Validará las convenciones de formato mediante checkstyle
 3. Detectará la presencia de (ciertos) code smells
 4. Validará la cobertura del proyecto

## Entrega del proyecto

Para entregar el proyecto, crear un tag llamado `entrega-final`. Es importante que antes de realizarlo se corra la validación
explicada en el punto anterior. Se recomienda hacerlo de la siguiente forma:

```
mvn clean verify && git tag entrega-final && git push origin HEAD --tags
```

## Configuración del IDE (IntelliJ)

### Usar el SDK de Java 17

1. En **File/Project Structure...**, ir a **Project Settings | Project**
2. En **Project SDK** seleccionar la versión 17 y en **Project language level** seleccionar `17 - Sealed types, always-strict floating-point semantics`

![image](https://user-images.githubusercontent.com/39303639/228126065-221b9851-fb96-4f7f-a8e1-010732dc7ef6.png)

### Usar fin de linea unix
1. En **File/Settings...**, ir a **Editor | Code Style**.
2. En la lista **Line separator**, seleccionar `Unix and OS X (\n)`.

![image](https://user-images.githubusercontent.com/39303639/228126546-352289fa-8feb-4b39-99db-d8b860915fea.png)

### Tabular con dos espacios

1. En **File/Settings...**, ir a **Editor | Code Style | Java | Tabs and Indents**.
2. Cambiar **Tab size**, **Indent** y **Continuation indent** a 2, 2 y 4 respectivamente:

![image](https://user-images.githubusercontent.com/39303639/228127009-8c84ea72-969b-4e05-b311-45e3688a4164.png)

### Ordenar los imports

1. En **File/Settings...**, ir a **Editor | Code Style | Java | Imports**.
2. Cambiar **Class count to use import with '*'** y **Names count to use static import with '*'** a un número muy alto (ej: 99).
3. En **Import Layout**, dejarlo como se muestra a continuación:
    - `import static all other imports`
    - `<blank line>`
    - `import all other imports`

![image](https://user-images.githubusercontent.com/39303639/228126787-36f9ecff-27f2-4b99-bf11-a6bd89f67087.png)

### Instalar y configurar Checkstyle

1. Instalar el plugin https://plugins.jetbrains.com/plugin/1065-checkstyle-idea:
2. En **File/Settings...**, ir a **Tools | Checkstyle**.
3. Configurarlo activando los Checks de Google y la versión de Checkstyle `== 8.35`:

![image](https://user-images.githubusercontent.com/39303639/228126437-3d2f0137-3180-4221-a789-a057d920ae4e.png)
