/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

/**
 * Clase de excepción para las operaciones con grafos
 *
 * @author Reyes Ruiz
 */
public class GraphException extends Exception {

    /**
     * Crea una excepción con el mensaje especificado
     *
     * @param message El mensaje a mostrar
     */
    public GraphException(String message) {
        super(message);
    }

}
