package ajaxdemo;

import info.debatty.java.stringsimilarity.JaroWinkler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompararPalabras {

    private FreeLingCliente free;
    private JaroWinkler jaroWinkler;
    private double MINIMO_APROXIMACION = 0.7;
    private HashMap<String, String[]> sinonimosEstado;
    private HashMap<String, String[]> sinonimosNombresEstado;
    private HashMap<String, String[]> sinonimosPartesComputador;

    public void CompararPalabras(String frase) {
        jaroWinkler = new JaroWinkler();
        free = new FreeLingCliente("172.17.2.98", 1024);
        sinonimosEstado = new HashMap<String, String[]>();
        sinonimosEstado.put("apagado", new String[]{"apagado", "no enciende"});
        sinonimosEstado.put("parpadeando", new String[]{"apagado", "no enciende"});//FIXME: Cambiar sinonimos

        sinonimosNombresEstado = new HashMap<String, String[]>();
        sinonimosNombresEstado.put("sonido", new String[]{"sonido", "ruido", "pitidos", "zumbido"});

        sinonimosPartesComputador = new HashMap<String, String[]>();
        sinonimosPartesComputador.put("pantalla", new String[]{"pantalla", "monitor", "televisor"});
        sinonimosPartesComputador.put("computador", new String[]{"computador", "PC", "CPU", "Ordenador", "Procesador"});
    }

    public void desintegrarFrase(String frase) {
        String palabras[] = frase.split(" ");
        String nombreEstado = null;
        String estado;
        Computador computador = new Computador();
        for (int indice = 0; indice < palabras.length; indice++) {
            String palabraAnterior = indice > 1 ? palabras[indice - 1] : "";
            
            estado = obtenerEstado(palabras[indice], palabraAnterior);
            obtenerNombreEstado(palabras[indice], nombreEstado);
            obtenerParteDeComputador(computador, palabras[indice]);
            
            if (nombreEstado != null && estado != null) {
                HashMap<String, String> mapEstado = new HashMap<>();
                mapEstado.put(nombreEstado, estado);
                nombreEstado = null;
                estado = null;
            }
        }
    }

    private String obtenerEstado(String palabra, String anterior) {
        double ranking = 0.0;
        for (Map.Entry<String, String[]> arregloSinonimo : sinonimosEstado.entrySet()) {
            for (int i = 0; i < arregloSinonimo.getValue().length; i++) {
                String sinonimo = arregloSinonimo.getValue()[i];
                String palabraCombinada = sinonimo.split(" ").length > 1 ? anterior + palabra : palabra;

                double presicionPalabra = jaroWinkler.similarity(sinonimo, palabraCombinada);
                if (ranking < presicionPalabra) {
                    ranking = presicionPalabra;
                }
            }
            if (ranking > MINIMO_APROXIMACION) {
                return arregloSinonimo.getKey();
            }
        }
        return null;
    }

    private String obtenerPalabra(HashMap<String, String[]> entidad, String palabra) {
        double ranking = 0.0;
        String palabraEncontrada = "";
        for (Map.Entry<String, String[]> arregloSinonimo : entidad.entrySet()) {
            for (int i = 0; i < arregloSinonimo.getValue().length; i++) {
                String sinonimo = arregloSinonimo.getValue()[i];
                double presicionPalabra = jaroWinkler.similarity(sinonimo, palabra);
                if (ranking < presicionPalabra) {
                    ranking = presicionPalabra;
                }
            }
            if (ranking > MINIMO_APROXIMACION) {
                palabraEncontrada = arregloSinonimo.getKey();
                return palabraEncontrada;
            }
        }
        return palabraEncontrada;
    }

    private void obtenerParteDeComputador(Computador computador, String palabra) {
        String parte = obtenerPalabra(sinonimosPartesComputador, palabra);
        if (parte.isEmpty()) {
            computador.setNombre(parte);
        }
    }

    private void obtenerNombreEstado(String palabra, String nombreEstado) {
        String parte = obtenerPalabra(sinonimosPartesComputador, palabra);
        if (parte.isEmpty()) {
            nombreEstado = parte;
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
