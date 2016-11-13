<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Servicio Tecnico</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/estiloGlobal.css" rel="stylesheet" type="text/css"/>
        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        
        <script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>
    </head>
    <nav id="mainNav" class="navbar navbar-default navbar-fixed-top navbar-custom affix-top">
        <div class="container">
            <div class="navbar-header page-scroll">
                <a class="navbar-brand"> <i class="fa fa-cogs"></i>SERVICIO TÉCNICO</a>
            </div>
        </div>
    </nav>
    <body>
        <div class="container-fluid">            
            <div class="row body">
                <div class="chat_window">
                    <div class="top_menu">
                        <div class="buttons">
                            <div class="button close"></div>
                            <div class="button minimize"></div>
                            <div class="button maximize"></div>
                        </div>
                        <div class="title">Chat</div>
                    </div>
                    <ul class="messages" id="divMensajes"></ul>
                    <div class="bottom_wrapper clearfix"> 
                        <div class="message_input_wrapper">
                            <input id="txtMensaje" class="message_input" placeholder="Escribir mensaje..." />
                        </div>
                        <button id="btnEnviarMensaje" class="btn"><i class="fa fa-paper-plane-o"></i> Enviar </button>
                    </div>
                </div>
            </div>
            <div class="message_template">
                <li class="message appeared">
                    <div class="avatar"><i class="fa fa-3x"></i></div>
                    <div class="text_wrapper">
                        <div class="text"></div>
                    </div>
                </li>
            </div>
        </div>
        <script src="js/scriptGlobal.js" type="text/javascript"></script>
    </body
</html>