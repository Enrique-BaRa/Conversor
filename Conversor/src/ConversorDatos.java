import java.util.Scanner;

public class ConversorDatos {

    private static final String[] monedas = {"MXN", "USD", "COP", "CLP", "ARS", "BRL"};
    private static CurrencyConverterAPI converterAPI;

    public static void main(String[] args) {
        converterAPI = new CurrencyConverterAPI();
        Scanner scanner = new Scanner(System.in);

        System.out.println("***** Bienvenido al conversor de monedas *****");
        System.out.println("Seleccione la moneda de origen:" +
                "\n1. Pesos Mexicanos (MXN)" +
                "\n2. Dólares (USD)" +
                "\n3. Peso Colombiano (COP)" +
                "\n4. Peso Chileno (CLP)" +
                "\n5. Peso Argentino (ARS)" +
                "\n6. Real Brasileño (BRL)");

        int opcionOrigen = scanner.nextInt();

        if (opcionOrigen < 1 || opcionOrigen > 6) {
            System.out.println("Opción no válida. Seleccione una opción de 1 a 6.");
            return;
        }

        String monedaOrigen = monedas[opcionOrigen - 1];

        if (!converterAPI.ConversionActual(monedaOrigen)) {
            System.out.println("Hubo un problema al obtener los datos de la API.");
            return;
        }

        System.out.println("Ingrese el valor en " + monedaOrigen + " a convertir:");
        double valorOrigen = scanner.nextDouble();

        System.out.println("\n" + valorOrigen + " " + monedaOrigen + " equivalen a:\n");
        for (String monedaDestino : monedas) {
            if (!monedaDestino.equals(monedaOrigen)) {
                double tasaDestino = converterAPI.getConversionRate(monedaDestino);
                if (tasaDestino != -1) {
                    double resultado = valorOrigen * tasaDestino;
                    System.out.println(monedaDestino + ": " + resultado);
                } else {
                    System.out.println(monedaDestino + ": No disponible.");
                }
            }
        }
    }
}
