package ajaxdemo;

import Rule.BooleanRuleBase;
import Rule.Clause;
import Rule.Condition;
import Rule.Rule;
import Rule.RuleVariable;
import java.util.HashMap;

public class LaBase {

    private final BooleanRuleBase base = new BooleanRuleBase("ServicioTecnio");

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
    private RuleVariable puerto_usb = null;

    //objeto falla
    private RuleVariable falla = null;

    public LaBase() {

        //<editor-fold desc="Inicializar las variables de la clase" defaultstate="collapsed">
        //Se definne las variables con las que se trabajará para hacer las reglas de inferencia
        //Se define que la variable es una variable para las reglas
        this.problema = new RuleVariable(base, "Existe un problema");
        //Se discriminan cuales son los posibles estados para la variables, o cuales son las que se controlan
        this.problema.setLabels("Si No");

        this.verificacion = new RuleVariable(base, "Resultado de un consejo de verificación");
        this.verificacion.setLabels("Si No");

        this.pantalla = new RuleVariable(base, "Estado del monitor");
        this.pantalla.setLabels("Apagado Mesaje_con_la_palabra_boot Parpadeos Encendida No_es_relevante");

        this.computador = new RuleVariable(base, "Estado del computador");
        this.computador.setLabels("Apagado Encendida No_es_relevante Reiniciando");

        this.puerto_usb = new RuleVariable(base, "Valida estado puertos USB");
        this.puerto_usb.setLabels("Defectuosa No_relevante");

        this.sonido = new RuleVariable(base, "Posibles Sonidos");
        this.sonido.setLabels("Sonido_metalico_en_CPU Pitido_largo No_es_relevante");

        this.entorno = new RuleVariable(base, "Variables en el entorno");
        this.entorno.setLabels("Se_fue_la_luz_anteriormente Se_presenta_una_temperatura_elevada No_es_relevante");

        this.mensajeEnSistema = new RuleVariable(base, "Mensajes del sistema Operativo No_es_relevante");
        this.mensajeEnSistema.setLabels("Poca_memoria_cierre_programas No_es_relevante");

        this.raton = new RuleVariable(base, "Estado del raton");
        this.raton.setLabels("El_puntero_en_pantalla_no_responde Apagado No_es_relevante");

        //estado del teclado
        this.teclado = new RuleVariable(base, "Estado del teclado No_es_relevante");
        this.teclado.setLabels("El_teclado_no_responde");

        this.falla = new RuleVariable(base, "Servivio tecnico dice:");
        //Es la condición con la que se hará relaciones entre variables y estados
        Condition cond = new Condition("=");
        //</editor-fold>
        //<editor-fold desc="Base de conocimientos para cuando es mensaje de boot" defaultstate="collapsed">
        //Es el mensaje que se debe mostrar cuando la regla se cumpla
        String errBoot = "Algún sector de arranque del disco duro se ha dañado, intente utilizando software para sectores dañados (Recomendación: Hiren’s Boot CD) ¿Te funcionó?--problema(no, verificacion no)";
        /*
         *Se crea una regla de inferencia, el primer parámetro es un objeto BooleanRuleBase, 
         *segundo parámetro es un arreglo de clausulas que definen en que estado debe estar cada variable para que se cumpla
         * Y el último parámetro es la clausula que fija que la variable "falla" tendrá el error enviado seguidamente
         */
        Rule fallaArranque = new Rule(base, "Error arranque", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "Mesaje_con_la_palabra_boot"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errBoot));

        //La siguiente regla tiene exactamente los mismo parámetros pero con la variable verificación en NO, esto es en caso de que el usuario haya dicho que no funciona
        String errVerificacionBoot = "Puedes encender el computador y seleccionar compatibilidad de CD-ROM, el CD debe tener la imagen del sistema operativo";
        errVerificacionBoot += "lo que permite reemplazar los archivos de arranque--limpiarInformacion";
        Rule fallaVerificaArranque = new Rule(base, "Error arranque no funcionó", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "Mesaje_con_la_palabra_boot"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errBoot));
        //</editor-fold>
        //<editor-fold desc="Base de conocimientos para soporte" defaultstate="collapsed">
        String errDD1 = "Es posible que los cabezales del lector-escritura, han aterrizado sobre la superficie de los platos. En estos casos es necesario reemplazar el disco duro";
        Rule fallaDiscoDuro1 = new Rule(base, "Error en disco duro 1", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(sonido, cond, "Sonido_metalico_en_CPU"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
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
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "Se_fue_la_luz_anteriormente"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));

        String errMemRAM1 = "Esto sucede porque la memoria RAM no tiene la capacidad que exiges, la mejor solución sería comprar una memoria RAM o utilizar pendrive que 'ayude' la ram";
        Rule fallaMemRAM1 = new Rule(base, "Falla en la memoria RAM", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "Poca_memoria_cierre_programas"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errMemRAM1));

        String errMemRAM2 = "Diagnostico:Error de memoria RAM, lo normal es que esté mal puesta o que esté dañada.\n"
                + "Solucion: Colocarla adecuadamente o en caso de que se encuentre dañada reemplazarla.";

        Rule fallaMemRAM2 = new Rule(base, "Falla en la memoria RAM", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(sonido, cond, "Pitido_largo"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
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
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, errDD1));
        String ansPantalla = "¿La pantalla está encendida? --pantalla--bool(Encendido,Apagado)";
        Rule fallaIncompletaSonido = new Rule(base, "Sólo se sabe del sonido", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(sonido, cond, "Sonido_metalico_en_CPU"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansPantalla));
        //</editor-fold>
        //<editor-fold desc="Describe en caso de tener problemas o no" defaultstate="collapsed">
        String ansExisteProblema = "Cuéntame que sucede por favor";
        Rule mensajeProblema = new Rule(base, "Tiene un problema", new Clause[]{
            new Clause(problema, cond, "Si")}, new Clause(falla, cond, ansExisteProblema));
        String ansNoExisteProblema = "Bueno, en caso de tener algún problema no dudes en escribir";
        Rule mensajeSinProblema = new Rule(base, "No tiene un problema", new Clause[]{
            new Clause(problema, cond, "No")}, new Clause(falla, cond, ansNoExisteProblema));
        //</editor-fold>
        //<editor-fold desc="Base de conocimientos para pantalla apagada" defaultstate="collapsed">
        String ansComputador = "¿El computador está encendido? --computador--bool(Encendido,Apagado)";
        Rule fallaIncompletaPantalla = new Rule(base, "Sólo se sabe de la pantalla", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
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
            new Clause(puerto_usb, cond, "No_es_relevante"),
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
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansFuenteCable));

        String ansTarjetaVideo = "Verifica que la ram esté limpia e intenta nuevamente, sino funciona es necesario cambiar la tarjeta de vídeo--limpiarInformacion";
        Rule fallaVerificarComputador = new Rule(base, "No funcionó al prender el computador", new Clause[]{
            new Clause(pantalla, cond, "Apagado"),
            new Clause(computador, cond, "Apagado"),
            new Clause(verificacion, cond, "No"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
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
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansTarjetaVideo));
        //</editor-fold>
        //<editor-fold desc="Fallas en el funcionamiento del mouse" defaultstate="collapsed">
        String ansRaton = "Recuerda que el ratón o mouse debe estar conectado en el puerto de color verde,en caso que sea de entrada USB intente conectarlo en otro puerto ¿Te funcionó?--problema--bool(No, verificacion no)";
        Rule fallaRaton = new Rule(base, "El computador está prendido", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "Apagado"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansRaton));

        String ansRatonSinSolucion = "Verifica que el mouse funcioné en otros computadores, de no ser así es necesario cambiar el mouse.--limpiarInformacion";
        Rule verifiacionFallaRaton = new Rule(base, "No funcionó conectando mouse", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No"),
            new Clause(puerto_usb, cond, "No_es_relevante"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "Apagado"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansRatonSinSolucion));
        //</editor-fold>
        //<editor-fold desc="Fallas en conectores de usb" defaultstate="collapsed">
        String ansUSBSolucion = "Verifique que la memoria USB esté haciendo contacto, sino intente conectar en otro puerto. ¿Te funcionò?--problema--bool(No, verificacion no)";
        Rule verifiacionFallaUSB = new Rule(base, "No funciona conector de usb", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No_es_relevante"),
            new Clause(puerto_usb, cond, "Defectuoso"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansUSBSolucion));
        String ansVerificaUSBSolucion = "Si la usb no funciona en otros computadores es necesario cambiar la usb, si funciona en otros equipos y en ningún puerto del computador verifique que los drivers estés correctos";
        Rule fallaVerificaUSB = new Rule(base, "No funciona conector de usb", new Clause[]{
            new Clause(pantalla, cond, "No_es_relevante"),
            new Clause(computador, cond, "No_es_relevante"),
            new Clause(verificacion, cond, "No"),
            new Clause(puerto_usb, cond, "Defectuoso"),
            new Clause(problema, cond, "No_es_relevante"),
            new Clause(sonido, cond, "No_es_relevante"),
            new Clause(entorno, cond, "No_es_relevante"),
            new Clause(mensajeEnSistema, cond, "No_es_relevante"),
            new Clause(raton, cond, "No_es_relevante"),
            new Clause(teclado, cond, "No_es_relevante"),}, new Clause(falla, cond, ansUSBSolucion));
        //</editor-fold>
    }

    /**
     * Permite enviar el valor de las variables de la clase para que el objeto BooleanRuleBase pueda 
     * determinar la falla más acertada, según la base de conocimiento que está declarada en 
     * la parte superior
     * @param componentes - Es un HashMap donde la clave es el nombre de la variable y el valor es su estado
     * @return String - Envía la falla o mensaje que más se adapte a las variables
     */
    public String evaluar(HashMap<String, String> componentes) {
        String problema = validarExistenciaParametro(componentes, "problema");
        this.problema.setValue(
                componentes.size() > 1 && !problema.equals("No") ? "No_es_relevante"
                : validarExistenciaParametro(componentes, "problema")
        );
        this.puerto_usb.setValue(validarExistenciaParametro(componentes, "puerto_usb"));
        this.verificacion.setValue(validarExistenciaParametro(componentes, "verificacion"));
        this.computador.setValue(validarExistenciaParametro(componentes, "computador"));
        this.pantalla.setValue(validarExistenciaParametro(componentes, "pantalla"));
        this.sonido.setValue(validarExistenciaParametro(componentes, "sonido"));
        this.entorno.setValue(validarExistenciaParametro(componentes, "entorno"));
        this.mensajeEnSistema.setValue(validarExistenciaParametro(componentes, "mensajeEnSistema"));
        this.raton.setValue(validarExistenciaParametro(componentes, "raton"));
        this.teclado.setValue(validarExistenciaParametro(componentes, "teclado"));

        this.base.forwardChain();
        return this.falla.getValue();
    }

    /**
     * Verifica que una variable exista en el HashMap de componentes y envía su estado
     * en caso contrario envía "No_es_relevante" ya que de esta forma se declaran en las reglas
     * @param componentes
     * @param nombreComponente
     * @return 
     */
    private String validarExistenciaParametro(HashMap<String, String> componentes, String nombreComponente) {
        if (componentes.containsKey(nombreComponente)) {
            return componentes.get(nombreComponente);
        }
        return "No_es_relevante";
    }
}
