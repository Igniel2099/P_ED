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


    /**
     * Este método resuelve multiplicaciones y divisiones en una operación combinada.
     * Primero, encuentra el primer signo de multiplicación o división en la lista de signos.
     * Luego, usando el índice de este signo en la lista, localiza en la lista de
     * números el primer operando en la misma posición de índice y el segundo operando en
     * la siguiente posición (índice + 1). Realiza la operación correspondiente entre estos dos operandos (multiplicación o división),
     * y luego elimina tanto el signo como los números involucrados de sus respectivas listas, reemplazándolos con el resultado obtenido.
     * @param resultado Se pasa un parámetro de tipo Resultado que tiene los números y signos separados.
     */
    public Resultado calcularMultiplicacionYDivision(Resultado resultado) throws ExcepcionCalcular {
        int i = 0;
        while (i < resultado.operaciones.size()) {
            Character signo = resultado.operaciones.get(i);
            double total = 0.0;
            if (signo.equals('*') || signo.equals('/')) {

                Double primero = resultado.numeros.remove(i);
                Double segundo = resultado.numeros.remove(i);

                if (signo.equals('*')) {
                    total = primero * segundo;

                } else if (signo.equals('/')) {
                    if (segundo == 0) {
                        throw new ExcepcionCalcular("No se puede dividir por 0");
                    }
                    total = primero / segundo;
                }
                resultado.operaciones.remove(i);
                resultado.numeros.add(i, total);

            } else {
                i++;
            }
        }

        return resultado;
    }

    /**
     * Hace lo mismo que el método anterior pero trata con exponentes y raíces cuadradas.
     * @param resultado Se pasa un parámetro de tipo Resultado que tiene los números y signos separados.
     * @return Devuelve un objeto de tipo Resultado ya que todavía hay más cálculos por hacer.
     */
    public Resultado calcularExponentesYRaizCuadrada(Resultado resultado) throws ExcepcionCalcular {
        int i = 0;
        while (i < resultado.operaciones.size()) {

            Character signo = resultado.operaciones.get(i);
            double total = 0.0;

            if (signo.equals('^') || signo.equals('√')) {
                if (signo.equals('^')) {

                    Double base = resultado.numeros.remove(i);
                    Double exponente = resultado.numeros.remove(i);

                    total = Math.pow(base, exponente);

                } else if (signo.equals('√')) {
                    Double base = resultado.numeros.remove(i);
                    if (base < 0) {
                        throw new ExcepcionCalcular("No se pueden sacar raíces cuadradas de números negativos.");
                    }

                    total = Math.sqrt(base);
                }

                resultado.operaciones.remove(i);
                resultado.numeros.add(i, total);
            } else {
                i++;
            }
        }
        return resultado;
    }

    
}



