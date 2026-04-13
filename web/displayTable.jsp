<%-- 
    Document   : displayTable
    Created on : 02 17, 26, 9:53:34 AM
    Author     : Vinz
--%>

<%@page import="java.util.List"%>
<%@page import="controller.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="styles/displayTableStyles.css">
<div class="results-container">
    <table class="db-table">
        <thead>
            <th>Email</th>
            <th>Password</th>
            <th>User Role</th>
            <th>Actions</th>
        </thead>
        <tbody>
            <%
                List<User> userList = (List<User>)session.getAttribute("userList");
                
                String loggedInEmail = (String) session.getAttribute("email");
                
                if (userList != null && !userList.isEmpty()){
                    for (User user : userList){
            %>
            <tr>
                <td><%= user.getEmail() %></td>
                <td><%= user.getPassword() %></td>
                <td><%= user.getUserRole() %></td>
                <td>
                    <div class="actions-container">
                        <form action="${pageContext.request.contextPath}/CrudServlet" method="POST">
                            <input type="hidden" name="email" value="<%= user.getEmail()%>">
                            <button type="submit" name="action" value="openUpdate" class="edit-btn">Edit</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/CrudServlet" method="POST">
                            <input type="hidden" name="email" value="<%= user.getEmail()%>">
                            
                            <% if (user.getEmail().equals(loggedInEmail)) { %>
                                <button type="button" class="delete-btn" disabled title="You are not allowed to delete your account!" style="opacity: 0.5; cursor: not-allowed;">Delete</button>
                            <% } else { %>
                                <button type="submit" name="action" value="delete" class="delete-btn" onclick="return confirm('Are you sure you want to delete? This cannot be undone');">Delete</button>
                            <% } %>
                        </form>
                    </div>
                </td>
            </tr>
            <%
                    }
                } else{ 
            %>
            <tr>
                <td colspan="5" class="no-record">No Records Found. Click "View Records" to load database.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>