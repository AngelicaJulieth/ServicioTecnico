var scriptMensajes = {
    listaPropiedades: [],
    configurarControles: function () {
        vista = scriptMensajes;
        document.getElementById('txtMensaje').addEventListener('keypress', vista.validarTeclaEnterEnviar);
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
    validarTeclaEnterEnviar: function (e) {
        if (e.which == 13 || e.keyCode == 13) {
            vista.enviarMensajeCliente();
        }
    },
    enviarMensajeAServidor: function () {
        var mensaje = vista.validarRespuestaBooleana();
        var objetoEnviar = {
            mensaje: mensaje,
            listaPropiedades: JSON.stringify(vista.listaPropiedades),
            objetoPendiente: JSON.stringify(vista.respuestaServidor ? vista.respuestaServidor.objetoPendiente : null)
        };

        $.ajax({
            url: 'ActionServlet',
            type: 'POST',
            data: objetoEnviar,
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
        if (!vista.respuestaServidor) {
            return mensaje;
        }
        var pendiente = vista.respuestaServidor.objetoPendiente;
        var posiblesEstados = pendiente ? pendiente.estado : "";
        if (pendiente && posiblesEstados.startsWith("bool")) {
            var estados = posiblesEstados.substring(0, posiblesEstados.length - 1);
            var afirmativo = mensaje.startsWith("si") || mensaje.startsWith("sí") || mensaje.startsWith("Si") || mensaje.startsWith("Sí");
            var negativo = mensaje.startsWith("no") || mensaje.startsWith("No");
            estados = posiblesEstados.split("(")[1];
            estados = estados.substring(0, estados.length -1);

            if (afirmativo) {
                vista.listaPropiedades.push({nombre: pendiente.nombre, estado: estados.split(",")[0]});
                return "";
            }
            if (negativo) {
                var negacion = estados.split(",")[1].indexOf("verificacion");
                if (negacion !== -1) {
                    vista.listaPropiedades.push({nombre: "verificacion", estado: "No"});
                }else{
                    vista.listaPropiedades.push({nombre: pendiente.nombre, estado: estados.split(",")[1]});
                }
                return "";
            }
            return pendiente.nombre + " " + mensaje;
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