/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package IPC1_Practica1_202307764;

/**
 *
 * @author daish
 */
import java.util.*;
//ACTUALIZADO 

public class IPC1_Practica1_202307764 {

    static final int TAMANO_TABLERO = 14;
    static final int MIN_CARACTERES = 5;
    static final int MAX_CARACTERES = 10;
    static char[][] tablero = new char[TAMANO_TABLERO][TAMANO_TABLERO];
    static String[] palabras = new String[20];
    static int palabrasCount = 0;
    static Random random = new Random();
    static String nombreUsuario;
    static int carnetUsuario;
    static String seccionUsuario;
    static String[] historial = new String[10];
    static int historialCount = 0;
    static String[] topJugadores = new String[3];
    static int[] topPuntos = new int[3];

    public static void main(String[] args) {
        inicializarTablero();
        menuPrincipal();
    }

    private static void inicializarTablero() {
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                tablero[i][j] = (char) ('A' + random.nextInt(26));
            }
        }
    }

    //INICIA EL MENU PRINCIPAL CON TODAS LAS FUNCIONES
    private static void menuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(" MENU PRINCIPAL ");
            System.out.println("1. Nueva Partida");
            System.out.println("2. Historial de Partidas");
            System.out.println("3. Puntuaciones Mas Altas");
            System.out.println("4. Informacion del Estudiante");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 ->
                    nuevaPartida(scanner);
                case 2 ->
                    mostrarHistorial();
                case 3 ->
                    mostrarTopPuntuaciones();
                case 4 ->
                    datosEstudiante(scanner);
                case 5 -> {
                    System.out.println("Gracias por jugar! Saliendo...");
                    return;
                }
                default ->
                    System.out.println("Opcion invalida ");
            }
        }
    }

    private static void nuevaPartida(Scanner scanner) {
        System.out.print("Ingrese su nombre completo: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese su carnet: ");
        int carnetUsuario = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese su seccion: ");
        String seccionUsuario = scanner.nextLine();

        System.out.println("\n--- Estudiante ---");
        System.out.println("Nombre: " + nombreUsuario);
        System.out.println("Carnet: " + carnetUsuario);
        System.out.println("Seccion: " + seccionUsuario);

        menuSopaDeLetras(scanner);
    }

    private static void datosEstudiante(Scanner scanner) {
        System.out.println("Nombre: Julio Eduardo Gustavo Alvarado Ramirez");
        System.out.println("Carnet: 202307764");
        System.out.println("Seccion: E");

    }

    //EL MENU CON LOS METODOS PARA LA SOPA DE LETRAS Y JUGAR
    private static void menuSopaDeLetras(Scanner scanner) {
        while (true) {
            System.out.println("--- Sopa de Letras ---");
            System.out.println("1. Agregar palabras");
            System.out.println("2. Modificar palabra");
            System.out.println("3. Eliminar palabra");
            System.out.println("4. Mostrar palabras");
            System.out.println("5. Jugar");
            System.out.println("6. Regresar");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 ->
                    agregarPalabras(scanner);
                case 2 ->
                    modificarPalabra(scanner);
                case 3 ->
                    eliminarPalabra(scanner);
                case 4 ->
                    mostrarPalabras();
                case 5 ->
                    jugar(scanner);
                case 6 -> {
                    System.out.println("Regresando al menu principal...");
                    return;
                }
                default ->
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void mostrarHistorial() {
        System.out.println("--- Historial de Partidas ---");
        for (int i = 0; i < historialCount; i++) {
            System.out.println(historial[i]);
        }
    }

    private static void mostrarTopPuntuaciones() {
        System.out.println("--- Top 3 Puntuaciones Mas Altas ---");
        for (int i = 0; i < 3; i++) {
            if (topJugadores[i] != null) {
                System.out.println(topJugadores[i] + " - " + topPuntos[i] + " puntos");
            }
        }
    }

    private static void jugar(Scanner scanner) {
        if (palabrasCount == 0) {
            System.out.println("No hay palabras registradas. Agregue palabras primero.");
            return;
        }
        int puntos = 25;
        int errores = 0;
        int palabrasEncontradas = 0;
        int palabrasPendientes = palabrasCount;

        colocarPalabrasEnTablero();
        while (true) {
            imprimirTablero();
            System.out.println("Palabras encontradas: " + palabrasEncontradas + " / " + palabrasCount);
            System.out.println("Puntos actuales: " + puntos);
            System.out.print("Ingrese una palabra encontrada o (SALIR) para terminar: ");
            String entrada = scanner.nextLine().toUpperCase();
            if (entrada.equals("SALIR")) {
                break;
            }
            boolean encontrada = false;
            for (int i = 0; i < palabrasCount; i++) {
                if (palabras[i] != null && palabras[i].equals(entrada)) {
                    reemplazarPalabraConHash(entrada);
                    encontrada = true;
                    puntos += entrada.length();
                    palabrasEncontradas++;
                    palabrasPendientes--;
                    palabras[i] = null;
                    break;
                }
            }
            if (!encontrada) {
                errores++;
                puntos -= 5;
                System.out.println("Palabra no encontrada. Errores cometidos: " + errores + "/4");
                if (errores >= 4) {
                    System.out.println("Has perdido la partida.");
                    break;
                }
            }
        }
        String resultado = nombreUsuario + " - Puntos: " + puntos + ", Errores: " + errores + ", Palabras encontradas: " + palabrasEncontradas;
        if (historialCount < historial.length) {
            historial[historialCount++] = resultado;
        }
        actualizarTopPuntuaciones(nombreUsuario, puntos);
    }

    private static void actualizarTopPuntuaciones(String jugador, int puntos) {
        for (int i = 0; i < 3; i++) {
            if (topJugadores[i] == null || puntos > topPuntos[i]) {
                for (int j = 2; j > i; j--) {
                    topJugadores[j] = topJugadores[j - 1];
                    topPuntos[j] = topPuntos[j - 1];
                }
                topJugadores[i] = jugador;
                topPuntos[i] = puntos;
                break;
            }
        }
    }

    //METODOS PARA AGREGAR, MODIFICAR, ELIMINAR y MOSTRAR LAS PALABRAS INGRESADAS
    private static void agregarPalabras(Scanner scanner) {
        System.out.print("Ingrese el numero de palabras que desea agregar: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < cantidad; i++) {
            if (palabrasCount >= palabras.length) {
                System.out.println("No se pueden agregar mas palabras.");
                return;
            }

            System.out.print("Ingrese las palabras entre 5 y 10 caracteres: ");
            String palabra = scanner.nextLine().toUpperCase();

            if (palabra.length() >= MIN_CARACTERES && palabra.length() <= MAX_CARACTERES) {
                palabras[palabrasCount++] = palabra;
            } else {
                System.out.println("Longitud invalida. Intente de nuevo.");
                i--;
            }
        }
    }

    private static void modificarPalabra(Scanner scanner) {
        System.out.print("Ingrese la palabra a modificar: ");
        String palabra = scanner.nextLine().toUpperCase();
        for (int i = 0; i < palabrasCount; i++) {
            if (palabras[i].equals(palabra)) {
                System.out.print("Ingrese la nueva palabra: ");
                String nuevaPalabra = scanner.nextLine().toUpperCase();
                if (nuevaPalabra.length() >= MIN_CARACTERES && nuevaPalabra.length() <= MAX_CARACTERES) {
                    palabras[i] = nuevaPalabra;
                } else {
                    System.out.println("Longitud invalida.");
                }
                return;
            }
        }
        System.out.println("Palabra no encontrada.");
    }

    private static void eliminarPalabra(Scanner scanner) {
        System.out.print("Ingrese la palabra a eliminar: ");
        String palabra = scanner.nextLine().toUpperCase();
        for (int i = 0; i < palabrasCount; i++) {
            if (palabras[i].equals(palabra)) {
                palabras[i] = palabras[--palabrasCount];
                palabras[palabrasCount] = null;
                System.out.println("Palabra eliminada.");
                return;
            }
        }
        System.out.println("Palabra no encontrada.");
    }

    private static void mostrarPalabras() {
        System.out.println("Palabras ingresadas:");
        for (int i = 0; i < palabrasCount; i++) {
            System.out.println("- " + palabras[i]);
        }
    }

    //METODO DE JUGAR CON LAS CLASES PARA REEMPLAZAR LAS PALABRAS ENCONTRADAS POR #
    private static void colocarPalabrasEnTablero() {
        for (int i = 0; i < palabrasCount; i++) {
            String palabra = palabras[i];
            int fila = random.nextInt(TAMANO_TABLERO);
            int columna = random.nextInt(TAMANO_TABLERO - palabra.length() + 1);
            for (int j = 0; j < palabra.length(); j++) {
                tablero[fila][columna + j] = palabra.charAt(j);
            }
        }
    }

    private static void imprimirTablero() {
        for (char[] fila : tablero) {
            for (char c : fila) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private static void reemplazarPalabraConHash(String palabra) {
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                if (buscarYReemplazar(i, j, palabra)) {
                    return;
                }
            }
        }
    }

    private static boolean buscarYReemplazar(int fila, int columna, String palabra) {
        int len = palabra.length();
        if (columna + len <= TAMANO_TABLERO) {
            for (int i = 0; i < len; i++) {
                if (tablero[fila][columna + i] != palabra.charAt(i)) {
                    return false;
                }
            }
            Arrays.fill(tablero[fila], columna, columna + len, '#');
            return true;
        }
        return false;
    }

}
