package ajaxdemo;

import Rule.BooleanRuleBase;
import Rule.Clause;
import Rule.Condition;
import Rule.Rule;
import Rule.RuleVariable;
import java.util.HashMap;

public class LaBase {

    private final BooleanRuleBase base = new BooleanRuleBase("Cursos");

    private RuleVariable pantalla = null;
    private RuleVariable sonido = null;
    private RuleVariable entorno = null;
    private RuleVariable mensajeEnSistema = null;
    private RuleVariable raton = null;
    private RuleVariable teclado = null;
    private RuleVariable computador = null;
    private RuleVariable problema = null;
    private RuleVariable fuente = null;
    private RuleVariable verificacion = null;

    //objeto falla
    private RuleVariable falla = null;

    public LaBase() {

        this.problema = new RuleVariable(base, "Existe un problema");
        this.problema.setLabels("Si No");
        this.verificacion = new RuleVariable(base, "Resultado de un consejo de verificación");
        this.verificacion.setLabels("Si No");

        //<editor-fold desc="Posibles estados" defaultstate="collapsed">
        //variables en pantalla
        this.pantalla = new RuleVariable(base, "Estado del monitor");
        this.pantalla.setLabels("Apagado Mesaje_con_la_palabra_boot Parpadeos Encendida No_es_relevante");
        this.computador = new RuleVariable(base, "Estado del computador");
        this.computador.setLabels("Apagado Encendida No_es_relevante Reiniciando");
        /*this.fuente = new RuleVariable(base, "Estado de la fuente de energía");
        this.fuente.setLabels("Apagado Encendida No_es_relevante");*/

        //variables en Sonido
        this.sonido = new RuleVariable(base, "Posibles Sonidos");
        this.sonido.setLabels("Sonido_metalico_en_CPU Pitido_largo No_es_relevante");

        //variables en el entorno
        this.entorno = new RuleVariable(base, "Variables en el entorno");
        this.entorno.setLabels("Se_fue_la_luz_anteriormente Se_presenta_una_temperatura_elevada No_es_relevante");

        //mensaje del sistema operativo
        this.mensajeEnSistema = new RuleVariable(base, "Mensajes del sistema Operativo No_es_relevante");
        this.mensajeEnSistema.setLabels("Poca_memoria_cierre_programas No_es_relevante");

        //estado del raton
        this.raton = new RuleVariable(base, "Estado del raton");
        this.raton.setLabels("El_puntero_en_pantalla_no_responde No_es_relevante");

        //estado del teclado
        this.teclado = new RuleVariable(base, "Estado del teclado No_es_relevante");
        this.teclado.setLabels("EL_teclado_no_responde");

        this.falla = new RuleVariable(base, "Servivio tecnico dice:");
        //</editor-fold>
        //<editor-fold desc="Base de conocimientos para soporte" defaultstate="collapsed">
        Condition cond = new Condition("=");
        String errDD1 = "Diagnostico: Se refiere a que los cabezales del lector-escritura, han aterrizado sobre la superficie de los platos.\n"
                + "Solucion: Para este caso no hay solución, sólo reemplazarlo por otro disco duro.";

        Rule fallaDiscoDuro1 = new Rule(base, "Error en disco duro 1", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(sonido, cond, "Sonido_metalico_en_CPU"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));

        String errDD2 = "Diagnostico: Uno de los sectores de arranque del disco duro se ha dañado el cual no permite completar el proceso para el inicio del sistema.\n"
                + "Solucion: Realizar un diagnóstico general del disco duro con algún software para reparar sectores dañados. (Software Recomendado Hiren’s Boot CD).";

        Rule fallaDiscoDuro2 = new Rule(base, "Error en disco duro 2", new Clause[]{
            new Clause(pantalla, cond, "Mesaje_con_la_palabra_boot"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));

        String errFuente1 = "Diagnostico:Esto es debido a que los valores de voltaje aplicados a la placa basen no son adecuados.\n"
                + "Solucion:Revisar las tensiones que suministra la fuente de alimentación, hay programas que indican el voltaje que entran a la placa base (“Everest”).";

        /*Rule fallaFuente1 = new Rule(base, "Falla en la fuente de alimentación 1.", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));*/

        String errFuente2 = "Diagnostico:Una subida inesperada de tensión va a provocar en la mayoría de los casos la avería de la fuente de alimentación.\n"
                + "Solucion:Conectar la fuente de poder a un sistema de energía interrumpida (UPS) los mayores problemas de sobretensión se dan al volver a restablecerse el suministro de energía cuando este se ha cortado.";

        Rule fallaFuente2 = new Rule(base, "Falla en la fuente de alimentación 2.", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "Se_fue_la_luz_anteriormente"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));

        String errMemRAM1 = "Diagnostico:Lo más probable es que el equipo se encuentre infectado de virus y está generando procesos que hacen que se ocupe el espacio de memoria al máximo.\n"
                + "Solucion: Instalar un antivirus y mantenlo actualizado.";

        Rule fallaMemRAM1 = new Rule(base, "Falla en la memoria RAM", new Clause[]{
            new Clause(pantalla, cond, "Encendida"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "Poca_memoria_cierre_programas"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));

        String errMemRAM2 = "Diagnostico:Error de memoria RAM, lo normal es que esté mal puesta o que esté dañada.\n"
                + "Solucion: Colocarla adecuadamente o en caso de que se encuentre dañada reemplazarla.";

        Rule fallaMemRAM2 = new Rule(base, "Falla en la memoria RAM", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(sonido, cond, "Pitido_largo"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));

        String errCPU = "Diagnostico:Fallos en la CPU, validar si la tarjeta de video está conectada correctamente.\n"
                + "Solucion: a)Cambio de Memoria RAM.\n b)Revisar conectores del Motherboard";

        Rule fallaCPU = new Rule(base, "Falla CPU", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));

        String errMouse = "Diagnostico:Fallos en la CPU, validar si la tarjeta de video está conectada correctamente.\n"
                + "Solucion: a)Cambio de Memoria RAM.\n b)Revisar conectores del Motherboard";

        Rule fallaMouse = new Rule(base, "Falla de Mouse", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "El_puntero_en_pantalla_no_responde"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));
        //</editor-fold>
        //<editor-fold desc="Base de conocimientos para soporte" defaultstate="collapsed">
        String ansExisteProblema = "Cuéntame que sucede por favor";
        Rule mensajeProblema = new Rule(base, "Tiene un problema", new Clause[]{
            new Clause(problema, cond, "Si")}, new Clause(falla, cond, ansExisteProblema));
        String ansNoExisteProblema = "Bueno, en caso de tener algún problema no dudes en escribir";
        Rule mensajeSinProblema = new Rule(base, "No tiene un problema", new Clause[]{
            new Clause(problema, cond, "No")}, new Clause(falla, cond, ansNoExisteProblema));
        //</editor-fold>
        String ansPantalla = "¿La pantalla está encendida? --pantalla--bool(Encendido,Apagado)";
        Rule fallaIncompletaSonido = new Rule(base, "Sólo se sabe del sonido", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(sonido, cond, "Sonido_metalico_en_CPU"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansPantalla));
        //</editor-fold>
        //<editor-fold desc="Base de conocimientos para pantalla apagada">
        String ansComputador = "¿El computador está encendido? --computador--bool(Encendido,Apagado)";
        Rule fallaIncompletaPantalla = new Rule(base, "Sólo se sabe de la pantalla", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansComputador));
        String ansEncenderPC = "Enciende el computador y verifica que prenda la pantalla, ¿Te funcionó? --problema--bool(No, verificacion No)";
        Rule fallaVerificarPantalla = new Rule(base, "El computador está apagado", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(computador, cond, "Apagado"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansEncenderPC));
        String ansFuenteCable = "Verifica que los cables que conectan al computador y a la corriente estén bien ¿Te funcionó?--problema--bool(No, verificacion No)";
        Rule fallaFuenteCable = new Rule(base, "El computador está prendido", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(computador, cond, "Encendido"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansEncenderPC));
        String ansTarjetaVideo = "Verifica que la ram esté limpia e intenta nuevamente, sino funciona es necesario cambiar la tarjeta de vídeo";
        Rule fallaVerificarComputador = new Rule(base, "No funcionó al prender el computador", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(computador, cond, "Apagado"),
            new Clause(verificacion, cond, "No"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansTarjetaVideo));
        Rule fallaVerificarFuenteCable = new Rule(base, "El computador está prendido", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(computador, cond, "Encendido"),
            new Clause(verificacion, cond, "No"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansTarjetaVideo));
        //</editor-fold>
    }

    public String evaluar(HashMap<String, String> componentes) {
        String problema = validarExistenciaParametro(componentes, "problema");
        this.problema.setValue(
                componentes.size() > 1 &&  !problema.equals("No")? "No_es_relevante"
                        : validarExistenciaParametro(componentes, "problema")
        );
        this.verificacion.setValue(validarExistenciaParametro(componentes, "verificacion"));
        this.computador.setValue(validarExistenciaParametro(componentes, "computador"));
        this.pantalla.setValue(validarExistenciaParametro(componentes, "pantalla"));
        this.sonido.setValue(validarExistenciaParametro(componentes, "sonido"));
        this.entorno.setValue(validarExistenciaParametro(componentes, "entorno"));
        this.mensajeEnSistema.setValue(validarExistenciaParametro(componentes, "mensaje en sistema"));
        this.raton.setValue(validarExistenciaParametro(componentes, "raton"));
        this.teclado.setValue(validarExistenciaParametro(componentes, "teclado"));

        this.base.forwardChain();
        return this.falla.getValue();
    }

    private String validarExistenciaParametro(HashMap<String, String> componentes, String nombreComponente) {
        if (componentes.containsKey(nombreComponente)) {
            return componentes.get(nombreComponente);
        }
        return "No_es_relevante";
    }
}
