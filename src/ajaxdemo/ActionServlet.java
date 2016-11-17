package ajaxdemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import info.debatty.java.stringsimilarity.JaroWinkler;
import java.util.List;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {

    private JaroWinkler jaroWinkler;
    private static final long serialVersionUID = 1L;
//	private ArrayList<Persona> personas = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Double similitud = 0d;
        jaroWinkler = new JaroWinkler();
        PrintWriter out = response.getWriter();
        StringBuilder respuesta = new StringBuilder("La respuesta es: ");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<Computador> listaPropiedadesConfiguradas = gson.fromJson(request.getParameter("listaPropiedades"), new TypeToken<List<Computador>>() {
        }.getType());
        response.setContentType("application/json");
        String mensaje = request.getParameter("mensaje");
        similitud = jaroWinkler.similarity("no está encendido", "enciende");
        respuesta.append(similitud.toString());

        if (!mensaje.equals("")) {
            CompararPalabras comparacionPalabra = new CompararPalabras();
            comparacionPalabra.inicializarVariables();
            comparacionPalabra.desintegrarFrase(mensaje);
            listaPropiedadesConfiguradas = comparacionPalabra.convertirEnHashArregloPropiedades(listaPropiedadesConfiguradas, respuesta);
            respuesta = new StringBuilder(comparacionPalabra.enviarABaseConocimiento());
        }
        Respuesta rta = new Respuesta();
        rta.setMensaje(respuesta.toString());
        rta.setListaPropiedades(listaPropiedadesConfiguradas);

        out.print(gson.toJson(rta));
    }

}
