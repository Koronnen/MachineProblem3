<html>
    <head>
        <link rel="stylesheet" href="styles/errorStyles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SQL Error</title>
    </head>
    <body>
        <header>
            <jsp:include page="<%= (String)application.getAttribute("header") %>" />
        </header>
        <main>  
            <h1>Uh oh!</h1>
            <p>SQL Error detected, check logs(maybe)</p>
             <a href="index.jsp"><<< Go back</a>
        </main>
        <footer>
            <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
        </footer>
    </body>
</html>