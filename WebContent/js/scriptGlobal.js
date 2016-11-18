var scriptMensajes = {
    listaPropiedades: [],
    configurarControles: function () {
        vista = scriptMensajes;
        document.getElementById('btnEnviarMensaje').addEventListener('click', vista.enviarMensajeCliente);
        vista.pintarMensaje('Bienvenido a servicio técnico', 'SERVIDOR');
    },
    enviarMensajeCliente: function () {
        var mensaje = document.getElementById('txtMensaje').value;
        if (mensaje.trim() === '') {
            return;
        }
        vista.pintarMensaje(mensaje, 'CLIENTE');
        vista.enviarMensajeAServidor();
    },
    enviarMensajeAServidor: function () {
        var mensaje = vista.validarRespuestaBooleana();
        
        $.ajax({
            url: 'ActionServlet',
            type: 'POST',
            data: {mensaje: mensaje, listaPropiedades: vista.listaPropiedades},
            success: vista.recibirRespuestaServidor
        });
        document.getElementById('txtMensaje').value = '';
    },
    recibirRespuestaServidor: function (respuesta) {
        vista.listaPropiedades = respuesta.listaPropiedades ? respuesta.listaPropiedades : [];
        if (respuesta.mensaje.trim() !== '') {
            vista.respuestaServidor = respuesta;
            vista.pintarMensaje(respuesta.mensaje, 'SERVIDOR');
        }
    },
    validarRespuestaBooleana: function () {
        var mensaje = document.getElementById('txtMensaje').value.toLowerCase();
        if(!vista.respuestaServidor){
            return mensaje;
        }
        var pendiente = vista.respuestaServidor.objetoPendiente;
        var posiblesEstados = pendiente ? pendiente.estado : "";
        if (pendiente && posiblesEstados.startsWith("bool")) {
            var estados = posiblesEstados.substring(0, posiblesEstados.length - 1);
            var afirmativo = mensaje.startsWith("si") || mensaje.startsWith("sí");
            var negativo = mensaje.startsWith("no");
            estados = posiblesEstados.split("(")[1];
            
            if(afirmativo){
                vista.listaPropiedades.push({nombre: pendiente.nombre, estado: estados.split(",")[0]});
                return "";
            }
            if(negativo){
                vista.listaPropiedades.push({nombre: pendiente.nombre, estado: estados.split(",")[1]});
                return "";
            }
            return pendiente.nombre; 
        }
        return mensaje;
    },
    pintarMensaje: function (mensaje, origen) {
        var orientacion = origen === 'SERVIDOR' ? 'left' : 'right';
        var tipoicon = origen === 'SERVIDOR' ? 'fa-laptop' : 'fa-user';
        var templateMensaje = $($('.message_template').clone().html());

        templateMensaje.addClass(orientacion);
        templateMensaje.find('.text').html(mensaje);
        templateMensaje.find('.avatar i').addClass(tipoicon);
        $('#divMensajes').append(templateMensaje);
    }
};
scriptMensajes.configurarControles();