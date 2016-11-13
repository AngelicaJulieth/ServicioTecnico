package ajaxdemo;

import info.debatty.java.stringsimilarity.JaroWinkler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompararPalabras {

    private FreeLingCliente free;
    private JaroWinkler jaroWinkler;
    private double MINIMO_APROXIMACION = 0.7;
    private String saludos[] = {"hola", "buenos dias", "buenas tardes", "buenas noches"};
    private String estadosComputador[] = {"apagado", "parpadeando"};

    public void CompararPalabras(String frase) {
        jaroWinkler = new JaroWinkler();
        free = new FreeLingCliente("172.17.2.98", 1024);
    }

    public void obtenerPalabra(String palabra) {
        double ranking[] = {0.0, 0.0};
        for (int i = 0; i < estadosComputador.length; i++) {
            double presicionPalabra = jaroWinkler.similarity(estadosComputador[i], palabra);
            if (ranking[0] < presicionPalabra) {
                ranking[0] = presicionPalabra;
                ranking[1] = i;
            }
        }
        if (ranking[0] > MINIMO_APROXIMACION) {
            System.out.println(estadosComputador[(int) ranking[1]]);
        }
    }

    public String obtenerFrases(String f1, String f2) {
        String[] s1 = f1.split(" ");
        String[] s2 = f2.split(" ");

        String[] ap = new String[s1.length];
        for (int i = 0; i < ap.length; i++) {
            ap[i] = "";
        }

        int i = 0;
        for (String b : s1) {
            for (String c : s2) {
                if (jaroWinkler.similarity(b, c) > MINIMO_APROXIMACION) {
                    ap[i] = c;
                }
            }
            i++;
        }

        StringBuffer sb = new StringBuffer();

        for (String b : ap) {
            sb.append(b);
            sb.append(" ");
        }

        return sb.toString();
    }

    public String[][] normalizarMensaje(String mensaje) {
        try {
            String a[] = free.processSegment(mensaje.toLowerCase()).split("\n");
            String respuesta[][] = new String[a.length][];
            /**
             * [{la palabra mormal} {la palabra lema} {tipo gramatico}
             * {Probabilidad}] [{la palabra mormal} {la palabra lema} {tipo
             * gramatico} {Probabilidad}]
             */
            for (int i = 0; i < a.length; i++) {
                String aux[] = a[i].split(" ");
                if (aux.length > 1) {
                    respuesta[i] = aux;
                }
            }

            return respuesta;
        } catch (IOException ex) {
            Logger.getLogger(CompararPalabras.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
