package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.ForgotPasswordDAO;
import com.hospital.model.ForgotPassword;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        ForgotPassword forgot = new ForgotPassword();
        forgot.setEmail(email);

        ForgotPasswordDAO dao = new ForgotPasswordDAO();

        if (dao.resetPassword(forgot)) {

            response.getWriter().println(
                "<h3>Password Reset Successful</h3>" +
                "<p>Your temporary password is: <b>12345</b></p>" +
                "<a href='pages/login_page.html'>Go to Login</a>"
            );

        } else {

            response.getWriter().println(
                "<h3>Email not found!</h3>"
            );

        }
    }
}