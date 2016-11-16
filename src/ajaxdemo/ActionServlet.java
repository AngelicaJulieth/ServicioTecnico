package ajaxdemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import info.debatty.java.stringsimilarity.JaroWinkler;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
    private JaroWinkler jaroWinkler;
    private String saludos[] = {"hola", "buenos dias", "buenas tardes", "buenas noches"};

    private static final long serialVersionUID = 1L;
//	private ArrayList<Persona> personas = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Double similitud = 0d;
        jaroWinkler = new JaroWinkler();
        PrintWriter out = response.getWriter();
        StringBuilder respuesta = new StringBuilder("La respuesta es: ");
        
        response.setContentType("application/json");
        String mensaje = request.getParameter("mensaje");
        similitud = jaroWinkler.similarity("no está encendido", "enciende");
        respuesta.append(similitud.toString());
        
        /*if (!mensaje.equals("")) {
            // Creo el objeto persona y lo añado al arrayList
            resp += mensaje;
            
            PLN prueba = new PLN();
            String respAux[][];
            respAux = prueba.normalizarMensaje(mensaje);
            resp += Arrays.deepToString(respAux);
        }*/
        
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        out.print(gson.toJson(respuesta.toString()));

    }

}
