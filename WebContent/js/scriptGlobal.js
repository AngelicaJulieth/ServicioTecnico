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
/*
 (function () {
 var Message;
 Message = function (arg) {
 this.text = arg.text, this.message_side = arg.message_side;
 this.draw = function (_this) {
 return function () {
 var $message;
 $message = $($('.message_template').clone().html());
 $message.addClass(_this.message_side).find('.text').html(_this.text);
 $('.messages').append($message);
 return setTimeout(function () {
 return $message.addClass('appeared');
 }, 0);
 };
 }(this);
 return this;
 };
 $(function () {
 var getMessageText, message_side, sendMessage;
 message_side = 'right';
 getMessageText = function () {
 var $message_input;
 $message_input = $('.message_input');
 return $message_input.val();
 };
 sendMessage = function (text) {
 var $messages, message;
 if (text.trim() === '') {
 return;
 }
 $('.message_input').val('');
 $messages = $('.messages');
 message_side = message_side === 'left' ? 'right' : 'left';
 message = new Message({
 text: text,
 message_side: message_side
 });
 message.draw();
 return $messages.animate({scrollTop: $messages.prop('scrollHeight')}, 300);
 };
 $('#btnEnviarMensaje').click(function (e) {
 var msj = getMessageText();
 sendMessage(msj);
 $.post('ActionServlet', {
 mensaje: msj
 }, function (responseText) {
 return sendMessage(responseText);
 });
 });
 $('.message_input').keyup(function (e) {
 if (e.which === 13) {
 var msj = getMessageText();
 sendMessage(msj);
 $.post('ActionServlet', {
 mensaje: msj
 }, function (responseText) {
 return sendMessage(responseText);
 });
 }
 });
 sendMessage('Hola, ¿En qué te puedo ayudar?');
 });
 }.call(this));*/