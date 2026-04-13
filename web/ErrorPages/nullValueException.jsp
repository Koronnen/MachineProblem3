<%-- 
    Document   : nullValueException
    Created on : 02 16, 26, 7:28:40 PM
    Author     : Vinz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles/errorStyles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Null Value</title>
    </head>
    <body>
        <header>
            <jsp:include page="<%= (String)application.getAttribute("header") %>" />
        </header>
        <main>  
             <h1>Username (Email) and Password cannot be empty!</h1>
             <a href="index.jsp"><<< Go back</a>
        </main>
        <footer>
            <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
        </footer>
    </body>
</html>
