var scriptMensajes = {
    configurarControles: function () {
        vista = scriptMensajes;
        document.getElementById('btnEnviarMensaje').addEventListener('click', vista.enviarMensajeCliente);
        vista.pintarMensaje('Hola, ¿En qué te puedo ayudar?', 'SERVIDOR');
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
        var mensaje = document.getElementById('txtMensaje');
        $.ajax({
            url: 'ActionServlet',
            type: 'POST',
            data: {mensaje: mensaje.value},
            success: vista.recibirRespuestaServidor
        });
        mensaje.value = '';
    },
    recibirRespuestaServidor: function (respuesta) {
        if (respuesta.trim() !== '') {
            vista.pintarMensaje(respuesta, 'SERVIDOR');
        }
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
