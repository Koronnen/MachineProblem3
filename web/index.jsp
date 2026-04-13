<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles/indexStyles.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700&family=Open+Sans:wght@400;500&display=swap" rel="stylesheet">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<title>MySpace | Login</title>
<body>
    <header>
        <jsp:include page="<%= (String)application.getAttribute("header") %>" />
    </header>

    <main class="page-layout">
        <div class="branding">
            <h1><span class="yellow-accent">My</span>Space</h1>
            <p>Sign in to your account</p>
        </div>

        <div class="login-container">
            <h2>Sign In</h2>
            <p class="subtitle">Please enter your details to sign in.</p>
            
            <form action="${pageContext.request.contextPath}/loginServlet" method="POST">
                <div class="form-group">
                    <label for="email">Username</label>
                    <input type="email" id="email" name="email" placeholder="admin@example.com" maxlength="30">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" maxlength="20">
                </div>
                    <div class="g-recaptcha" data-sitekey="6Leisq4sAAAAAE0CRnKzI-jvprNJ8KfV2J5scKqB"></div>
                    <br/>
                <button type="submit">Sign In</button>
            </form>
        </div>
    </main>

    <footer>
        <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
    </footer>
</body>
</html>