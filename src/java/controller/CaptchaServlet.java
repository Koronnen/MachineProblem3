/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Vinz
 */
public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession s = request.getSession();
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            s.setAttribute("captchaResult", false);
            return;
        }
        boolean isValid = verifyCaptcha(gRecaptchaResponse);
        if (isValid) {
            s.setAttribute("captchaResult", true);
        } else {
            s.setAttribute("captchaResult", false);
        }
    }

    private boolean verifyCaptcha(String gRecaptchaResponse) throws
        IOException {
            ServletConfig config = getServletConfig();
            String SECRET_KEY = (String)config.getInitParameter("CaptchaSecretKey");
            String url = "https://www.google.com/recaptcha/api/siteverify";
            String params = "secret=" + SECRET_KEY + "&response=" +
            gRecaptchaResponse;
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            try {
                OutputStream os = con.getOutputStream();
                os.write(params.getBytes());
            }catch(IOException e){
                e.printStackTrace();
            }
            BufferedReader in = new BufferedReader(new
            InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getBoolean("success");
        }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
