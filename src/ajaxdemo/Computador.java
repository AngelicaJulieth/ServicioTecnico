package ajaxdemo;

import java.util.HashMap;
import java.util.List;

public class Computador {

    private String nombre;
    private HashMap<String, String> estados;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, String> getEstados() {
        return estados;
    }

    public void setEstados(HashMap<String, String> estados) {
        this.estados = estados;
    }

}
