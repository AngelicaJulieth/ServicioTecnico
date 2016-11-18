package ajaxdemo;

import java.util.List;

/**
 * Clase que define la respuesta entregada al cliente
 *
 * @author Julieth
 */
public class Respuesta {

    private String mensaje;
    private List<Computador> listaPropiedades;
    private Computador objetoPendiente;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Computador> getListaPropiedades() {
        return listaPropiedades;
    }

    public void setListaPropiedades(List<Computador> listaPropiedades) {
        this.listaPropiedades = listaPropiedades;
    }

    public Computador getObjetoPendiente() {
        return objetoPendiente;
    }

    public void setObjetoPendiente(Computador objetoPendiente) {
        this.objetoPendiente = objetoPendiente;
    }


}
