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
<p>
Las personas con movilidad reducida (permanente o temporal) a menudo enfrentan desafíos significativos en términos de movilidad en una ciudad. Además debemos considerar a las personas que llevan niños pequeños que también pueden verse afectados. <br>
</p>
<p>Algunos de los problemas comunes de movilidad incluyen: </p>
<p><strong>Accesibilidad:</strong> Muchas ciudades no están diseñadas para ser accesibles. Las aceras estrechas, los obstáculos en las calles y la falta de rampas para sillas de ruedas pueden dificultar que las personas con movilidad reducida se desplacen de manera segura y efectiva. </p> 
<p><strong>Transporte público:</strong> Los autobuses y trenes pueden ser difíciles de subir y bajar, y pueden no estar equipados con rampas o elevadores. Incluso si el transporte público es accesible, puede ser difícil para las personas con discapacidades físicas encontrar información sobre rutas y horarios. </p>
<p><strong>Edificios inaccesibles:</strong> Las instalaciones de los edificios en las ciudades pueden tener escaleras y puertas estrechas, y los baños pueden ser inaccesibles o no estar disponibles. </p>

<p>
Para moverse a través de las ciudades, estas personas suelen investigar a los lugares donde concurrirán pero muchas veces la información hallada no concuerda con la realidad. Por ejemplo, Martina necesita llevar a su bebé de 3 meses al pediatra que queda en la estación de Facultad de Medicina de la línea D de Subte de Buenos Aires. Al bajar en dicha estación se encuentra con que el ascensor no funciona y por lo tanto debe pedir ayuda para subir la escalera con el cochecito.</p>

<p>La situación mencionada en el párrafo anterior, y otras que seguramente el lector está visualizando, son las que trabajaremos en el presente trabajo práctico.</p>

<h4 style="color: lightgrey;">Nuestro Sistema</h4>

<p>A partir de la problemática identificada y presentada en la sección anterior, se presentan los requerimientos para el diseño y construcción de un Sistema de Monitoreo de Estado de Servicios.</p>

<h4 style="color: lightgrey;">Las entregas</h4>

<p>Serán 7 entregas, algunas orientadas específicamente a la inclusión de funcionalidades, mientras que otras se abocarán a la inclusión de algunos aspectos del diseño y herramientas tecnológicas para la implementación del mismo.</p>

<p>Las entregas previstas se muestran a continuación, aunque pueden sufrir algunas modificaciones en su alcance o fechas:</p>

| Nro | Titulo | Fecha de entrega |
| --- | ------ | ---------------- |
| 1   | [Modelado en Objetos – Parte I: Puesta a punto del entorno de desarrollo y primera iteración del diseño](https://github.com/dds-utn/2023-tpa-mama-grupo-17#modelado-en-objetos--parte-i-puesta-a-punto-del-entorno-de-desarrollo-y-primera-iteraci%C3%B3n-del-dise%C3%B1o) | 25-04-2023      |
| 2   | Modelado en Objetos – Parte II: Incrementando funcionalidades | 29/5 al 2 de junio      |
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
  <img src="https://i.ibb.co/XWxnkSh/cu.png" alt="Modelo de Casos de Uso" />
</p>

#### Modelo de Objetos
#### Documento

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
