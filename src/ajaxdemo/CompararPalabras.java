package ajaxdemo;

import info.debatty.java.stringsimilarity.JaroWinkler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompararPalabras {

    private FreeLingCliente free;
    private JaroWinkler jaroWinkler;
    private double MINIMO_APROXIMACION = 0.7;
    private HashMap<String, String[]> sinonimosEstado;
    private HashMap<String, String[]> sinonimosNombresEstado;
    private String[] sinonimosSaludos;
    private HashMap<String, String[]> sinonimosPartesComputador;
    private Computador computador;
    private HashMap<String, String> propiedadesComputador;
    //private List<Computador> partesComputador;

    public void inicializarVariables() {
        computador = new Computador();
        jaroWinkler = new JaroWinkler();
        sinonimosEstado = new HashMap<String, String[]>();
        sinonimosNombresEstado = new HashMap<String, String[]>();
        sinonimosPartesComputador = new HashMap<String, String[]>();
        sinonimosSaludos = new String[]{"hola", "buenos días", "buenos dias", "buenas noches", "buenas tardes", "buen día", "buenas"};

        //  partesComputador = new ArrayList<Computador>();
        propiedadesComputador = new HashMap<>();
        sinonimosEstado.put("parpadeando", new String[]{"apagado", "no enciende"});//FIXME: Cambiar sinonimos
        sinonimosEstado.put("encendida", new String[]{"encendida", "prendida", ""});
        sinonimosEstado.put("No_es_relevante", new String[]{"Está bien", "No hay problemas"});
        //sinonimosEstado.put("Error al cargar el sistema operativo", new String[]{"No carga sistema operativo", "Error al cargar el sistema operativo"});
        sinonimosEstado.put("apagado", new String[]{"apagado", "apagada", "no enciende", "no prende", "negra"});
        sinonimosEstado.put("Si", new String[]{"Si", "si", "correcto", "verdad", "afirmativo"});
        sinonimosEstado.put("No", new String[]{"No", "no"});

        sinonimosPartesComputador.put("sonido", new String[]{"sonido", "ruido", "pitidos", "zumbido", "suena"});
        sinonimosPartesComputador.put("pantalla", new String[]{"pantalla", "monitor", "televisor"});
        sinonimosPartesComputador.put("computador", new String[]{"computador", "PC", "CPU", "ordenador", "procesador"});
        sinonimosPartesComputador.put("mensaje", new String[]{"letrero", "aviso", "nota", "anuncio", "notificacion", "señal", "advertencia", "consejo", "indicacion", "comunicado", "observacion", "noticia", "sugerencia"});
    }

    public String desintegrarFrase(String frase) {
        String palabras[] = frase.split(" ");

        for (int indice = 0; indice < palabras.length; indice++) {
            String palabraAnterior = indice > 1 ? palabras[indice - 1] : "";
            String palabraActual = palabras[indice];
            Boolean esSaludo = obtenerSaludos(palabraActual, palabraAnterior);
            if (esSaludo) {
                return "Buen día, ¿Tienes algún problema con tu computador?";
            }
            if (palabraActual.toLowerCase() == "mi") {
                continue;
            }
            if (palabraActual == "del" || palabraActual == "de") {
                indice++;
                palabraAnterior += palabraActual;
                palabraActual = palabras[indice];
            }
            obtenerEstado(palabraActual, palabraAnterior, frase);
            //obtenerNombreEstado(palabraActual, nombreEstado);
            obtenerParteDeComputador(palabraActual);
        }
        return null;
    }

    private Boolean obtenerSaludos(String palabra, String anterior) {
        double ranking = 0.0;

        for (String saludo : sinonimosSaludos) {
            String palabraCombinada = saludo.split(" ").length > 1 ? anterior + palabra : palabra;

            double presicionPalabra = jaroWinkler.similarity(saludo, palabraCombinada);
            if (ranking < presicionPalabra) {
                ranking = presicionPalabra;
            }
        }
        return ranking > MINIMO_APROXIMACION;
    }

    private void obtenerEstado(String palabra, String anterior, String frase) {
        double rankingMayor = 0d;
        for (Map.Entry<String, String[]> arregloSinonimo : sinonimosEstado.entrySet()) {
            double ranking = 0.0;
            for (int i = 0; i < arregloSinonimo.getValue().length; i++) {
                double presicionPalabra = 0d;
                String palabraCombinada = palabra;
                String sinonimo = arregloSinonimo.getValue()[i];
                int cantidadPalabras = sinonimo.split(" ").length;

                if (cantidadPalabras > 3) {
                    palabraCombinada = frase;
                } else {
                    palabraCombinada = anterior + palabra;
                }

                presicionPalabra = jaroWinkler.similarity(sinonimo, palabraCombinada);
                if (ranking < presicionPalabra) {
                    ranking = presicionPalabra;
                }
            }
            if (ranking > MINIMO_APROXIMACION && ranking > rankingMayor) {
                rankingMayor = ranking;
                computador.setEstado(arregloSinonimo.getKey());
            }
        }
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

    private void obtenerParteDeComputador(String palabra) {
        String parte = obtenerPalabra(sinonimosPartesComputador, palabra);
        if (!parte.isEmpty()) {
            computador.setNombre(parte);
        }
    }

    public List<Computador> convertirEnHashArregloPropiedades(List<Computador> propiedades) {
        if (propiedades == null) {
            propiedades = new ArrayList<Computador>();
        }
        propiedades.add(computador);
        for (int indicePropiedades = 0; indicePropiedades < propiedades.size(); indicePropiedades++) {
            Computador propiedadComputador = propiedades.get(indicePropiedades);
            if (propiedadComputador.getNombre() != null && propiedadComputador.getEstado() != null) {
                propiedadesComputador.put(propiedadComputador.getNombre(), propiedadComputador.getEstado());
            }
        }
        return propiedades;
    }
    public List<Computador> convertirEnHashArregloLleno(List<Computador> propiedades) {
        for (int indicePropiedades = 0; indicePropiedades < propiedades.size(); indicePropiedades++) {
            Computador propiedadComputador = propiedades.get(indicePropiedades);
            if (propiedadComputador.getNombre() != null && propiedadComputador.getEstado() != null) {
                propiedadesComputador.put(propiedadComputador.getNombre(), propiedadComputador.getEstado());
            }
        }
        return propiedades;
    }

    public String enviarABaseConocimiento() {
        LaBase baseConocimiento = new LaBase();
        return baseConocimiento.evaluar(propiedadesComputador);
    }

    /*private void obtenerNombreEstado(String palabra, String nombreEstado) {
        String parte = obtenerPalabra(sinonimosPartesComputador, palabra);
        if (parte.isEmpty()) {
            nombreEstado = parte;
        }
    }*/
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
