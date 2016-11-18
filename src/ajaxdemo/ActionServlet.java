package ajaxdemo;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            PrintWriter out = response.getWriter();
            String respuesta = "No encontramos respuestas ¿puedes especificar más?";
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            List<Computador> listaPropiedadesConfiguradas = gson.fromJson(request.getParameter("listaPropiedades"), new TypeToken<List<Computador>>() {
            }.getType());
            response.setContentType("application/json");
            String mensaje = request.getParameter("mensaje");
            Respuesta rta = new Respuesta();
            rta.setMensaje(respuesta);
            rta.setListaPropiedades(listaPropiedadesConfiguradas);
            
            validarFrase(mensaje, rta, listaPropiedadesConfiguradas);

            out.print(gson.toJson(rta));
        } catch (Exception e) {

        }
    }

    private void validarFrase(String mensaje, Respuesta rta, List<Computador> listaPropiedadesConfiguradas) {
        String respuesta;
        CompararPalabras comparacionPalabra = new CompararPalabras();
        if (mensaje.equals("") && !listaPropiedadesConfiguradas.isEmpty()) {
            listaPropiedadesConfiguradas = comparacionPalabra.convertirEnHashArregloLleno(listaPropiedadesConfiguradas);
        } else {
            comparacionPalabra.inicializarVariables();
            String mensajeSaludo = comparacionPalabra.desintegrarFrase(mensaje);
            if (mensajeSaludo != null) {
                rta.setMensaje(mensajeSaludo);
                return;
            }
            listaPropiedadesConfiguradas = comparacionPalabra.convertirEnHashArregloPropiedades(listaPropiedadesConfiguradas);
        }

        respuesta = comparacionPalabra.enviarABaseConocimiento();
        if (respuesta.indexOf("||") > 0) {
            String[] parametros = respuesta.split("||");
            Computador computador = new Computador();
            Computador propiedadPendiente = new Computador();
            computador.setNombre(parametros[1]);
            computador.setEstado(parametros[2]);
            rta.setMensaje(parametros[0]);
            rta.setObjetoPendiente(propiedadPendiente);
        }
        return;

    }

}
