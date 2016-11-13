package ajaxdemo;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
//	private ArrayList<Persona> personas = new ArrayList<>();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //No hay peticiones get
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            
        

        
        PrintWriter out = response.getWriter();

        // Obtengo los datos de la peticion
        String mensaje = request.getParameter("mensaje");

        String resp = "la repsuesta es: ";

        if (!mensaje.equals("")) {
            // Creo el objeto persona y lo añado al arrayList
            resp += mensaje;
            PLN prueba = new PLN();
            String respAux[][];
            //respAux = prueba.normalizarMensaje(mensaje);
            //resp += Arrays.deepToString(respAux);

        }
        out.print(resp);

    }

}
