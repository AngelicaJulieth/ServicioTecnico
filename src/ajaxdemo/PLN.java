/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajaxdemo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ESTACION
 */
public class PLN {
    private static final String IPO_FREELING = "192.168.0.31";
    private final FreeLingCliente free;
    
    public PLN(){
        free = new FreeLingCliente(IPO_FREELING, 1024);
    }
    
    public String[][] normalizarMensaje(String mensaje){
        try {
            String a[] = free.processSegment(mensaje.toLowerCase()).split("\n");
            String respuesta[][] = new String[a.length][];
            
            for(int i=0; i<a.length; i++){
                String aux [] = a[i].split(" ");
                if(aux.length>1)
                    respuesta[i] = aux;
            }
            
            return respuesta;
        } catch (IOException ex) {
            Logger.getLogger(PLN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
  
}
