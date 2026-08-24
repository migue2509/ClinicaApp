# Hackathon-ClinicaApp

## Sistema de Gestión de Turnos Médicos - Java OOP



### Grupo 10



Sistema de gestión de turnos médicos desarrollado en Java aplicando

principios de Programación Orientada a Objetos (OOP).



##  Participantes



- Miguel Angel Ospina

- Michael Espinal Cardona

- Jhohan Sebastian Córdoba

- Martha Caro Muñoz



---



##  Descripción del proyecto



Este proyecto consiste en construir desde cero un sistema funcional

para gestionar pacientes, médicos y turnos de una clínica.



La aplicación funciona como un programa de consola desarrollado en

Java y aplica diferentes conceptos de Programación Orientada a Objetos,

como clases, objetos, encapsulamiento, herencia, interfaces, enumeraciones,

validaciones y colecciones.



El sistema mantiene los datos en memoria durante la sesión y utiliza

archivos CSV para conservar la información entre ejecuciones.



---

##  Objetivo



Desarrollar una aplicación de consola que permita:



- Registrar pacientes.

- Registrar médicos.

- Gestionar turnos médicos.

- Consultar pacientes y médicos.

- Asignar turnos.

- Cancelar turnos.

- Cambiar el estado de los turnos.

- Validar los datos ingresados.

- Guardar la información en archivos CSV.

- Cargar la información al iniciar nuevamente la aplicación.



---



##  Funcionamiento del sistema



El sistema utiliza un modelo en memoria con persistencia mediante

archivos CSV.



### Al iniciar



El programa ejecuta:



ClinicaService servicio = new ClinicaService();

DatosCSV.cargar(servicio);



Durante la ejecución:



Todas las operaciones trabajan sobre las listas almacenadas en memoria.



Al salir:



Cuando el usuario selecciona la opción de salida, los datos almacenados en memoria se escriben nuevamente en

pacientes.csv

medicos.csv

turnos.csv



De esta manera, la información permanece disponible para la siguiente

ejecución del programa.