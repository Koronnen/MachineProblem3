<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session == null || session.getAttribute("email") == null){
        throw new exception.InvalidSession("Invalid Session detected.");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles/successStyles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success</title>
        <script>
            function closeUpdateModal() {
                document.getElementById("updateModal").style.display = "none";
                // Optional: Redirect to a URL that clears the session attributes 
                // or just let the user continue.
            }
        </script>
    </head>
    <body>
        <header>
            <jsp:include page="<%= (String)application.getAttribute("header") %>" />
        </header>
        <main>
            <div class="user-container">
            <h1>Success!</h1>
            <% 
                String email = (String)session.getAttribute("email");
                String role = (String)session.getAttribute("role");
            %>
                <p class="credentials">Email: ${email}</p>
                <p class="credentials">Role: ${role}</p>

                <form action="${pageContext.request.contextPath}/logout" method="POST">
                    <input type="submit" value="Logout" method="POST" class="logoutbtn">
                </form>
            </div>
            <%
                if (role.equals("ADMIN")){
            %>
            <% 
            String message = (String)session.getAttribute("message");
            if (message != null){
            %>
                <p class="status-message">Status: ${message}</p>
            <%
                }
            %>
            <div class="db-container">
                <jsp:include page="displayTable.jsp" />
                <form action="${pageContext.request.contextPath}/CrudServlet" method="POST">
                    <button type="submit" name="action" value="viewresults">View Results</button>
                </form>
            </div>
            <div class="container">
            <h1>Add a User</h1>
                <div class="form-container">
                    <fieldset>
                        <p>Create</p>
                        <form action="${pageContext.request.contextPath}/CrudServlet" method="POST">
                            <label>Email: <input type="email" name="email" class="email input" id="email_create" /></label>
                            <label>Password: <input type="text" name="password" class="password input" id="password_create" min="0" max="130" /></label>
                            <label for="userrole_create">User Role: 
                                <select name="userrole" class="userrole input" id="userrole_create">
                                    <option value="ADMIN">ADMIN</option>
                                    <option value="GUEST">GUEST</option>
                                </select>
                            </label>
                            <input type="submit" name="action" value="Create" class="create">
                        </form>
                    </fieldset>
                </div>
            </div>
            <%-- Check if we are in "Update Mode" --%>
            <%
                String updateEmail = (String) session.getAttribute("updateEmail");
                String updatePass = (String) session.getAttribute("updatePass");
                String displayStyle = (updateEmail != null) ? "flex" : "none";
            %>

            <div id="updateModal" class="modal-backdrop" style="display: <%= displayStyle %>;">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2>Update User Settings</h2>
                        </div>
                    <form action="${pageContext.request.contextPath}/CrudServlet" method="POST">
                        <div class="modal-body">
                            <label>Target Email</label>
                            <input type="text" name="updateEmail" value="<%= updateEmail %>" readonly class="input greyed-out">

                            <label>Change Password</label>
                            <input type="text" name="updatePass" class="input" value="<%= updatePass %>">

                            <label>Assign Role</label>
                            <select name="updateUserRole" class="input">
                                <option value="ADMIN" <%= "ADMIN".equals(session.getAttribute("updateUserRole")) ? "selected" : "" %>>ADMIN</option>
                                <option value="GUEST" <%= "GUEST".equals(session.getAttribute("updateUserRole")) ? "selected" : "" %>>GUEST</option>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn cancel-btn" onclick="closeUpdateModal()">Cancel</button>
                            <button type="submit" name="action" value="update" class="btn save-btn">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
            <%
                }
            %>
        </main>
        <footer>
            <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
        </footer>
    </body> 
</html>
