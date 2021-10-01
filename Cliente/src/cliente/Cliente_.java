/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author AM
 */
public class Cliente_ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Iniciar el frame especificando la dirección IP para conectarse
        Cliente cliente = new Cliente("127.0.0.1");
        cliente.ejecutar();
    }
    
}
