import java.util.LinkedList;
import java.util.List;

/**
 * Esta clase busca simular el comportamiento de una calculadora
 */
public class Calculadora {
    /**
     * Esta clase record personalizada define un resultado para devolver otro
     * que tiene dos parámetros: una lista de números y signos de operación.
     */
    public record Resultado(List<Double> numeros, List<Character> operaciones) {}

    /**
     * Este método busca separar los números y los signos en listas separadas.
     * @param operacion La operación en cuestión que se va a tratar
     * @return Devuelve una lista de dos listas, la lista de números y la lista de signos.
     */
    public Resultado separarNumerosYSignos(String operacion) throws ExcepcionCalcular {

        if("+-*/^√".indexOf(operacion.charAt(operacion.length() - 1)) >= 0){
            throw new ExcepcionCalcular("Error: el último dato es un operador");
        }

        List<Double> listaNumeros = new LinkedList<>();
        List<Character> listaOperaciones = new LinkedList<>();

        StringBuilder bufferNumero = new StringBuilder();

        for (int i = 0; i < operacion.length(); i++) {
            char c = operacion.charAt(i);

            if (c == '√') {
                listaOperaciones.add(c);
            } else if (c == '-' && i == 0) {
                bufferNumero.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
                listaNumeros.add(Double.parseDouble(bufferNumero.toString()));
                bufferNumero.setLength(0);
                listaOperaciones.add(c);

            } else {
                bufferNumero.append(c);
            }
        }

        listaNumeros.add(Double.parseDouble(bufferNumero.toString()));

        return new Resultado(listaNumeros, listaOperaciones);
    }

    public double calcularSumaYResta(Resultado resultado) {
        double resultadoCalculo = resultado.numeros().removeFirst();

        while (!resultado.numeros().isEmpty()) {
            char primerSigno = resultado.operaciones().removeFirst();
            double siguienteNumero = resultado.numeros().removeFirst();

            if (primerSigno == '+') {
                resultadoCalculo += siguienteNumero;
            } else if (primerSigno == '-') {
                resultadoCalculo -= siguienteNumero;
            }
        }

        return resultadoCalculo;
    }
}
