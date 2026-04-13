<%-- 
    Document   : error_4
    Created on : 02 16, 26, 8:01:04 PM
    Author     : Vinz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles/errorStyles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>404</title>
    </head>
    <body>
        <header>
            <jsp:include page="<%= (String)application.getAttribute("header") %>" />
        </header>
        <main>  
            <h1>Uh oh!</h1>
            <p>404: Not Found.</p>
             <a href="<%= request.getHeader("referer") %>">Go Back</a>
        </main>
        <footer>
            <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
        </footer>
    </body>
</html>
