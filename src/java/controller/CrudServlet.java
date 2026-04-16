package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class CrudServlet extends HttpServlet {
    Connection conn;
    public void init(ServletConfig config) throws ServletException {
            super.init(config);

            try {	
                    Class.forName(config.getInitParameter("jdbcClassName"));
                    System.out.println("jdbcClassName: " + config.getInitParameter("jdbcClassName"));
                    String username = config.getInitParameter("dbUserName");
                    String password = config.getInitParameter("dbPassword");
                    StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL"))
                            .append("://")
                            .append(config.getInitParameter("dbHostName"))
                            .append(":")
                            .append(config.getInitParameter("dbPort"))
                            .append("/")
                            .append(config.getInitParameter("databaseName"));
                    conn = 
                      DriverManager.getConnection(url.toString(),username,password);
                    System.out.println("Done loading databases");
            } catch (SQLException sqle){
                    System.out.println("SQLException error occured - " 
                            + sqle.getMessage());
            } catch (ClassNotFoundException nfe){
                    System.out.println("ClassNotFoundException error occured - " 
                    + nfe.getMessage());
            }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        ServletContext context = getServletContext();
        String action = request.getParameter("action");
        String message = "";
        HttpSession session = request.getSession(false);

        if (conn == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        try {
            if ("Create".equals(action)) {
                String email = request.getParameter("email");
                String rawPass = request.getParameter("password");
                String password = Security.encrypt(rawPass, context);
                String role = request.getParameter("userrole");

                String query = "INSERT INTO USERS (Email, Password, UserRole) VALUES (?, ?, ?)";

                try {
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, email);
                    ps.setString(2, password);
                    ps.setString(3, role);
                    int rowCount = ps.executeUpdate();
                    message = (rowCount == 1) ? "Creation success!" : "Creation failed";
                    ps.close();
                }catch(SQLException e){e.printStackTrace();}

            } else if ("openUpdate".equals(action)) {
                String email = request.getParameter("email");
                String query = "SELECT * FROM USERS WHERE Email = ?";
                try{
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, email);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        session.setAttribute("updateEmail", rs.getString("Email"));
                        session.setAttribute("updatePass", Security.decrypt(rs.getString("Password"), context));
                        session.setAttribute("updateUserRole", rs.getString("UserRole"));
                    }
                }catch(SQLException e){e.printStackTrace();}
                request.getRequestDispatcher("success.jsp").forward(request, response);
                return;
           } else if (action.equals("delete")) {
                String email = request.getParameter("email");
                
                 String loggedInEmail = (String) session.getAttribute("email");

                    String query = "DELETE FROM USERS WHERE Email = ?";
                    try {
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, email);
                        int rowCount = ps.executeUpdate();
                        message = (rowCount == 1) ? "Deletion success!" : "Deletion failed";
                        ps.close();
                    } catch(SQLException e) {
                        e.printStackTrace();
                    }
                
                session.setAttribute("message", message);
                response.sendRedirect("CrudServlet?action=viewresults");
                return;
            } else if (action.equals("update")) {
                String email = request.getParameter("updateEmail");
                String rawPass = request.getParameter("updatePass");
                String pass = Security.encrypt(rawPass,context);
                String role = request.getParameter("updateUserRole");

                try {
                    String query = "UPDATE USERS SET Password = ?, UserRole = ? WHERE Email = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, pass);
                    ps.setString(2, role);
                    ps.setString(3, email);

                    int result = ps.executeUpdate();
                    message = (result == 1) ? "Update Successful." : "Update failed.";
                    ps.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    message = "Error: " + e.getMessage();
                } 

                if (email != null && email.equals(session.getAttribute("email"))) {
                    session.setAttribute("role", role);
                }

                session.removeAttribute("updateEmail");
                session.removeAttribute("updatePass");
                session.removeAttribute("updateUserRole");

                session.setAttribute("message", message);

                response.sendRedirect("CrudServlet?action=viewresults");
                return; 
            }
            else if (action.equalsIgnoreCase("viewresults")){
                List<User> userList = new ArrayList<User>();
                try {	
                    if (conn != null) {
                            userList = getList(userList);
                            
                            session.setAttribute("userList", userList);
                            request.getRequestDispatcher("success.jsp").forward(request, response);
                            return;
                    } else {
                        response.sendRedirect("error.jsp");
                        }
                    } catch (SQLException sql){
                        response.sendRedirect("error.jsp");
                    }
            }
            else if (action.equalsIgnoreCase("cancelUpdate")){
                session.removeAttribute("updateEmail");
                session.removeAttribute("updatePass");
                session.removeAttribute("updateUserRole");
                
                message = "Update cancelled";
            }
                session.setAttribute("message", message);
                response.sendRedirect("success.jsp");
                return;
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid format");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            }
    }
    public List<User> getList(List userList) throws SQLException{
        ServletContext context = getServletContext();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM USERS ORDER BY Email");
        while (rs.next()){
            User user = new User(
                rs.getString("Email"),
                Security.decrypt(rs.getString("Password"),context),
                rs.getString("UserRole")
            );
            userList.add(user);
        }
        rs.close();
        stmt.close();
        
        return userList;
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
