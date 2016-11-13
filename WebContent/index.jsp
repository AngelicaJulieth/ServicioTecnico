<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Chatbot Servicio Tecnico</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/Bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/estiloGlobal.css" rel="stylesheet" type="text/css"/>

        <script src="js/jquery/jquery-3.1.1.min.js" type="text/javascript"></script>

    </head>
    <nav>Chat para servicio técnico prueba</nav>
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
                    <ul class="messages"></ul>
                    <div class="bottom_wrapper clearfix"> 
                        <div class="message_input_wrapper">
                            <input class="message_input" placeholder="Escribir mensaje..." />
                        </div>
                        <div class="send_message">
                            
                        </div>
                    </div>
                </div>
                <div class="message_template">
                    <li class="message">
                        <div class="avatar"></div>
                        <div class="text_wrapper">
                            <div class="text"></div>
                        </div>
                    </li>
                </div>
            </div>
        </div>
        <script src="js/scriptGlobal.js" type="text/javascript"></script>
    </body
</html>